import io.github.aakira.napier.Napier

class Greeting {
    private val platform = getPlatform()

    fun greet(): String {
        Napier.i { "Gretings from ${platform.name}" }
        return "Hello, ${platform.name}!"
    }
    
    companion object {
        const val TAG = "Greeting"
    }
}