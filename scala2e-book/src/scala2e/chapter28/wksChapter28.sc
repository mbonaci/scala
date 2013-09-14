package scala2e.chapter28

object wksChapter28 {

  abstract class CCTherm {
	  val description: String
	  val yearMade: Int
	  val dateObtained: String
	  val bookPrice: Int      // in cents
	  val purchasePrice: Int  // in cents
	  val condition: Int      // 1 to 10
	
	  override def toString = description
	  def toXML =
	    <cctherm>
      <description>{description}</description>
      <yearMade>{yearMade}</yearMade>
      <dateObtained>{dateObtained}</dateObtained>
      <bookPrice>{bookPrice}</bookPrice>
      <purchasePrice>{purchasePrice}</purchasePrice>
      <condition>{condition}</condition>
    </cctherm>
	}

	// usage:
	val therm = new CCTherm {  // possible because Scala instantiates anonymous subclass
	  val description = "The joy of Clojure"
	  val yearMade = 2011
	  val dateObtained = "24.08.2013"
	  val bookPrice = 2400
	  val purchasePrice = 2000
	  val condition = 10
	} // therm: CCTherm = The joy of Clojure  //> therm  : scala2e.chapter28.wksChapter28.CCTherm = The joy of Clojure
	
	therm.toXML                               //> res0: scala.xml.Elem = <cctherm>
                                                  //|       <description>The joy of Clojure</description>
                                                  //|       <yearMade>2011</yearMade>
                                                  //|       <dateObtained>24.08.2013</dateObtained>
                                                  //|       <bookPrice>2400</bookPrice>
                                                  //|       <purchasePrice>2000</purchasePrice>
                                                  //|       <condition>10</condition>
                                                  //|     </cctherm>
	
	// to include curly braces in the XML text just double them:
	val xml = <a>{{brace yourself!}}</a>      //> xml  : scala.xml.Elem = <a>{brace yourself!}</a>
	
  val x1 = <a><b><c><d>hello</d></c></b></a>   \ "a"
                                                  //> x1  : scala.xml.NodeSeq = NodeSeq()
  val x2 = <a><b><c><d>hello</d></c></b></a>  \\ "a"
                                                  //> x2  : scala.xml.NodeSeq = NodeSeq(<a><b><c><d>hello</d></c></b></a>)
  val x3 = <a><b><c><d>hello</d></c></b></a>   \ "b"
                                                  //> x3  : scala.xml.NodeSeq = NodeSeq(<b><c><d>hello</d></c></b>)
  val x4 = <a><b><c><d>hello</d></c></b></a>  \\ "b"
                                                  //> x4  : scala.xml.NodeSeq = NodeSeq(<b><c><d>hello</d></c></b>)
  val x5 = <a><b><c><d>hello</d></c></b></a>   \ "c"
                                                  //> x5  : scala.xml.NodeSeq = NodeSeq()
  val x6 = <a><b><c><d>hello</d></c></b></a>  \\ "c"
                                                  //> x6  : scala.xml.NodeSeq = NodeSeq(<c><d>hello</d></c>)
  val x7 = <a><b><c><d>hello</d></c></b></a>   \ "d"
                                                  //> x7  : scala.xml.NodeSeq = NodeSeq()
  val x8 = <a><b><c><d>hello</d></c></b></a>  \\ "d"
                                                  //> x8  : scala.xml.NodeSeq = NodeSeq(<d>hello</d>)


  val joe = <employee
              name="JR"
              rank="dev"
              serial="8"/>                        //> joe  : scala.xml.Elem = <employee name="JR" rank="dev" serial="8"/>

	joe \ "@name"                             //> res1: scala.xml.NodeSeq = JR
	joe \ "@serial"                           //> res2: scala.xml.NodeSeq = 8
	
	
	// To parse back a 'CCTherm' instance:
	def fromXML(node: scala.xml.Node): CCTherm =
	  new CCTherm {
	    val description   = (node \ "description"  ).text
	    val yearMade      = (node \ "yearMade"     ).text.toInt
	    val dateObtained  = (node \ "dateObtained" ).text
	    val bookPrice     = (node \ "bookPrice"    ).text.toInt
	    val purchasePrice = (node \ "purchasePrice").text.toInt
	    val condition     = (node \ "condition"    ).text.toInt
	  }                                       //> fromXML: (node: scala.xml.Node)scala2e.chapter28.wksChapter28.CCTherm
	  
	  
	val th = fromXML (therm.toXML)            //> th  : scala2e.chapter28.wksChapter28.CCTherm = The joy of Clojure
}