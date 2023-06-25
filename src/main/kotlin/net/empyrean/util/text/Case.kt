package net.empyrean.util.text

object Case {
    val CAMEL2SNAKE_REPLACE: Regex = Regex("[a-z][A-Z]")

    fun camelToSnake(str: String): String {
        return CAMEL2SNAKE_REPLACE.replace(str) { match ->
            val first = match.value[0]
            val second = match.value[1]
            "${first}_${second.lowercase()}"
        }
    }
}