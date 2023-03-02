package at.aau.edu.lechl.se.singlephase.ui.primenumber

interface PrimeNumberFilter {

    /**
     * Filters characters out of the string which are no prime numbers.
     */
    fun filter(input: String): String
}

private val PRIME_BELOW_TEN = listOf('2', '3', '5', '7')

object PrimeNumberFilterImpl : PrimeNumberFilter {

    override fun filter(input: String): String {
        // according to specification we should filter out characters which are
        // not prime numbers. As a single character can only be between 0 and 9
        // we can avoid introducing some heavy logic here

        return input.filter { PRIME_BELOW_TEN.contains(it) }
    }

}