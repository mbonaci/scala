package scala2e.chapter20

object wksChapter20 {
  val jy = JP.Yen from US.Dollar * 100;           //> jy  : scala2e.chapter20.JP.Currency = 12110 JPY
  val ee = EU.Euro from jy;                       //> ee  : scala2e.chapter20.EU.Currency = 76,0 EUR
  val ud = US.Dollar from ee                      //> ud  : scala2e.chapter20.US.Currency = 100,0 USD
  US.Dollar * 100 + ud                            //> res0: scala2e.chapter20.US.Currency = 200,0 USD
  //US.Dollar + EU.Euro // type mismatch
}