package edu.sumdu.tss.elephant;

import edu.sumdu.tss.elephant.controller.*;
import edu.sumdu.tss.elephant.controller.api.ApiController;
import edu.sumdu.tss.elephant.helper.Keys;
import edu.sumdu.tss.elephant.helper.ViewHelper;
import edu.sumdu.tss.elephant.helper.exception.HttpException;
import edu.sumdu.tss.elephant.middleware.CSRFFilter;
import edu.sumdu.tss.elephant.middleware.CustomAccessManager;
import io.javalin.Javalin;
import io.javalin.core.util.JavalinLogger;
import io.javalin.http.Context;
import io.javalin.http.staticfiles.Location;
import io.javalin.plugin.openapi.OpenApiOptions;
import io.javalin.plugin.openapi.OpenApiPlugin;
import io.javalin.plugin.openapi.ui.ReDocOptions;
import io.javalin.plugin.openapi.ui.SwaggerOptions;
import io.swagger.v3.oas.models.info.Info;

import java.io.File;

public class Server {
    private static final boolean enableCors = true;
    private final Javalin app;

    {
    /* Load stats from GIT
        try {
            FileInputStream fis;
            Properties properties = new Properties();
            String propFileName = "git.properties";
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
            properties.load(inputStream);
            JavalinLogger.info(properties.getProperty("git.commit.id.full"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    */
        app = Javalin.create(
                        config -> {
                            config.addStaticFiles("/public", Location.CLASSPATH);
                            config.accessManager(CustomAccessManager.accessManager);
                            config.registerPlugin(new OpenApiPlugin(getOpenApiOptions()));
                        })
                .before("/", CSRFFilter::check)
                .before("/", CSRFFilter::generate)
                .before(context -> {
                    JavalinLogger.info("[" + context.method() + "] " + context.url());
                })
                .before(ViewHelper::defaultVariables)
                .after(ViewHelper::cleanupSession)
                .exception(HttpException.class, ViewHelper::userError)
                .exception(Exception.class, (e, ctx) -> {
                    ViewHelper.userError(new HttpException(e), ctx);
                });
        new ApiController(app);
        new BackupController(app);
        new DatabaseController(app);
        new TableController(app);
        new HomeController(app);
        new LoginController(app);
        new ProfileController(app);
        new RegistrationController(app);
        new ScriptsController(app);
        new SqlController(app);
    }

    public static void main(final String[] args) {
        if (args.length < 1) {
            throw new RuntimeException("File with properties must be specified on startup");
        }
        var file = new File(args[0]);
        Keys.loadParams(file);

        new Server().start(Integer.parseInt(Keys.get("APP.PORT")));
    }

    /**
     * Method sets the access to sending the requests to the server
     *
     * @param ctx Context that contains the header with access
     */
    public static void cors(final Context ctx) {
        ctx.header("Cache-Control", "no-cache, no-store");
        if (enableCors) {
            ctx.header("Access-Control-Allow-Origin", "*");
        }
    }

    public void start(final int port) {
        this.app.start(port);
    }

    public void stop() {
        this.app.stop();
    }

    private OpenApiOptions getOpenApiOptions() {
        Info applicationInfo = new Info()
                .version("1.0")
                .description("Elephant");
        return new OpenApiOptions(applicationInfo)
                .path("/swagger-docs")
                .ignorePath("/database/*")
                .ignorePath("/login/*")
                .ignorePath("/login")
                .ignorePath("/logout")
                .ignorePath("/registration")
                .ignorePath("/registration/*")
                .ignorePath("/")
                .ignorePath("/home")
                .ignorePath("/home/*")
                .ignorePath("/profile")
                .ignorePath("/profile/*")
                .swagger(new SwaggerOptions("/swagger").title("My Swagger Documentation"))
                .reDoc(new ReDocOptions("/redoc").title("My ReDoc Documentation"))
                .activateAnnotationScanningFor("edu.sumdu.tss.elephant.controller.api");

    }
}
