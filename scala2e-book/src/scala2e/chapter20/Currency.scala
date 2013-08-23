package scala2e.chapter20

/*
 * 
 * The task is to design a class Currency. A typical
 * instance of Currency would represent an amount of money
 * in dollars, euros, yen, or some other currency. 
 * It should be possible to do some arithmetic on currencies.
 * For instance, you should be able to add two amounts of 
 * the same currency. 
 * Or you should be able to multiply a currency amount by a 
 * factor representing an interest rate.
 * 
 */

abstract class CurrencyZone {
  type Currency <: AbstractCurrency
  val CurrencyUnit: Currency
  def make(x: Long): Currency
  
  
  abstract class AbstractCurrency {
	val amount: Long
	def designation: String
	
	override def toString =
	  ((amount.toDouble / CurrencyUnit.amount.toDouble)
	  formatted ("%." + decimals(CurrencyUnit.amount) + "f")
	  + " " + designation)
	
	def + (that: Currency): Currency =
	  make(this.amount + that.amount)	  
	  
	def * (factor: Long): Currency = 
	  make((this.amount * factor).toLong)

	def / (qu: Double) =
	  make((this.amount / qu).toLong)
	def / (that: Currency) =
	  this.amount.toDouble / that.amount
	  
	private def decimals(n: Long): Int =
	  if (n == 1) 0 else 1 + decimals(n / 100)
	  
	def from(other: CurrencyZone#AbstractCurrency): Currency =
      make(math.round(
        other.amount.toDouble * Converter.exchangeRate
          (other.designation)(this.designation)))
  }

}

object Converter {
  var exchangeRate = Map(
    "USD" -> Map("USD" -> 1.0   , "EUR" -> 0.7596,
                 "JPY" -> 1.211 , "CHF" -> 1.223),
    "EUR" -> Map("USD" -> 1.316 , "EUR" -> 1.0,
                 "JPY" -> 1.594 , "CHF" -> 1.623),
    "JPY" -> Map("USD" -> 0.8257, "EUR" -> 0.6272,
    			 "JPY" -> 1.0   , "CHF" -> 1.018),
    "CHF" -> Map("USD" -> 0.8108, "EUR" -> 0.6160,
    			 "JPY" -> 0.982 , "CHF" -> 1.0)
  )
}

object EU extends CurrencyZone {
  abstract class Euro extends AbstractCurrency {
    def designation = "EUR"
  }
  type Currency = Euro
  def make(cents: Long) = new Euro {
    val amount = cents
  }
  val Cent = make(1)
  val Euro = make(100)
  val CurrencyUnit = Euro
}

object JP extends CurrencyZone {
  abstract class Yen extends AbstractCurrency {
    def designation = "JPY"
  }
  type Currency = Yen
  def make(yen: Long) = new Yen {
    val amount = yen
  }
  val Yen = make(1)
  val CurrencyUnit = Yen
}

object US extends CurrencyZone {
  abstract class Dollar extends AbstractCurrency {
    def designation = "USD"
  }
  type Currency = Dollar
  def make(cents: Long): Dollar = 
    new Dollar { val amount = cents }
  val Cent = make(1)
  val Dollar = make(100)
  val CurrencyUnit = Dollar
 
}
