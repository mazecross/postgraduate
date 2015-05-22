Joe English provided a taxonomy of XML documents based on their declaration patterns of namespaces (the following definitions are taken from his email; read the email for more detail, but see note at the bottom of this page about names):

Let's say that an XML document is deceptive if it maps the same namespace prefix to two different namespace URIs at different points.
Conversely, a document is confusing if it maps two different namespace prefixes to the same namespace URI in different scopes.
A document is super-confusing if it maps two different namespace prefixes to the same URI in the same scope.
A document is ok if it is neither deceptive nor confusing.
A document is normal (or in namespace-normal form) if all namespace declarations appear on the root element and it is not super-confusing.
For this assignment you are to write a program in Java, using the standard DOM API, that checks XML documents for namespace normality and confusion, and reports on all pairs of namespace declarations which are confusing.

---

Your program should make use of the supplied stub NamespaceAnalyzer.java, and must use the standard DOM classes bundled with the latest JDK (i.e., Java Standard Edition 6 or later). Your submission should compile from the standard command line.

Input: A well-formed XML document.

Output: A namespace report file using the namespace report format described by namespaceAnalysisReport.dtd that reports

a general verdict for the namespace analysis of the whole input document from 
confusing
normal
neither (i.e., not confusing and not normal)
all occurrences of confusing namespace declarations in the input document.
Note, as with the prior version, you have a specific method that passes you a DOM and expects a DOM in return.

The output format is documented in a DTD: namespaceAnalysisReport.dtd. Your output must be valid wrt to that DTD, but doesn't need a DOCTYPE. Note that some of the format (e.g., the form of XPath used in the path attribute) is described in comments.

NOTE: FOLLOW THE STUB!!! You should NOT do any System.out stuff. You should NOT build a string. You should NOT parse. You should make the following method:  public Document check(Document srcfile)  work. It consumes a Document and it returns a Document.