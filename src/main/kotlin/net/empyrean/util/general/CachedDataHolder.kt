package net.empyrean.util.general

interface CachedDataHolder<D> {
    fun getOrCalculate(): D
}