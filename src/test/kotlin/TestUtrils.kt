internal fun getXmlResource(path:String) : String {
  return XmlTests::class.java.getResource("/$path").readText()
}