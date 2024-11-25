package jsp.develop.nativetoweb

import android.util.Log
import io.ktor.server.application.call
import io.ktor.server.engine.embeddedServer
import io.ktor.server.http.content.react
import io.ktor.server.http.content.singlePageApplication
import io.ktor.server.http.content.staticResources
import io.ktor.server.netty.Netty
import io.ktor.server.netty.NettyApplicationEngine
import io.ktor.server.response.respondFile
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import java.io.File

class LocalServer {
    private var server: NettyApplicationEngine? = null
    private var TAG: String = "KTOR_SERVER"

    fun start() {
        Log.d(TAG, "Current working directory: ${System.getProperty("user.dir")}")
        server = embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
            routing {
                singlePageApplication {
                    useResources = true
                    react("assets/www")
                }
            }
        }.start(wait = false)
        Log.d(TAG, "Server running at http://localhost:8080/")
    }

    fun stop() {
        server?.stop(1000, 10000)
        Log.d(TAG, "Server stopped.")
    }
}