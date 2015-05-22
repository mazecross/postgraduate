package uk.ac.manchester.cs.msc.ssd.saxcalc.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.EmptyStackException;
import java.util.Stack;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 29-Sep-2011
 */
public class CalculatorImpl {

	/**
	 * ZERO ARGUMENT CONSTRUCTOR - VERY IMPORTANT! DO NOT ALTER
	 */
	public CalculatorImpl() {
	}

	/**
	 * Computes the result to a calculation that is specified by an XML document
	 * that can be obtained from the given input stream.
	 * 
	 * @param is
	 *            The input stream from which the XML document can be obtained.
	 * @return The result of the calculation.
	 * @throws org.xml.sax.SAXException
	 *             If the document was invalid or there was some problem parsing
	 *             the calculation.
	 */
	public float computeResult(InputStream is) throws SAXException,
			NumberFormatException {
		System.out.println("Running calculator...");
		// TODO: Implementation.

		// setup factory (containing the parser) for the purpose of document
		// parsing.
		SAXParserFactory factory = SAXParserFactory.newInstance();
		// wrt to DTD.
		factory.setValidating(true);
		factory.setNamespaceAware(true);

		try {
			SAXParser parser = factory.newSAXParser();

			XMLReader reader = parser.getXMLReader();

			// setup error handling for when the input document is invalid.
			reader.setErrorHandler(new ErrorHandler() {

				@Override
				public void error(SAXParseException e) throws SAXException {
					System.out.println("ERROR at line " + e.getLineNumber());
					System.out.println(e.getMessage());
					throw new SAXException("errinvalid (SAXException)");
				}

				@Override
				public void fatalError(SAXParseException e) throws SAXException {
					System.out.println("FATAL ERROR at line "
							+ e.getLineNumber());
					System.out.println(e.getMessage());
					throw new SAXException("errinvalid (SAXException)");
				}

				@Override
				public void warning(SAXParseException e) throws SAXException {
					System.out.println("WARNING at line " + e.getLineNumber());
					System.out.println(e.getMessage());
				}
			});

			// initialize static stacks for calculation purposes and for
			// retrieving the final result.
			final Stack<String> operators = new Stack<String>();
			final Stack<Float> valuesStack = new Stack<Float>();
			final Stack<Integer> valueCountStack = new Stack<Integer>();

			// setup content handler when reading the input document element by
			// element.
			reader.setContentHandler(new DefaultHandler() {

				@Override
				public void startElement(String uri, String localName,
						String qName, Attributes attributes)
						throws SAXException {

					if (qName.equalsIgnoreCase("PLUS")) {
						operators.push(qName);
						valueCountStack.push(0);
					}

					if (qName.equalsIgnoreCase("MINUS")) {
						operators.push(qName);
						valueCountStack.push(0);

					}

					if (qName.equalsIgnoreCase("TIMES")) {
						operators.push(qName);
						valueCountStack.push(0);
					}

					if (qName.equalsIgnoreCase("DIVIDEDBY")) {
						operators.push(qName);
						valueCountStack.push(0);
					}

					if (qName.equalsIgnoreCase("INT")) {
						try {
							valuesStack.push(Float.parseFloat(attributes
									.getValue("value")));
						} catch (NumberFormatException nfe) {
							throw new NumberFormatException(
									"errnan (NumberFormatException)");
						}

						valueCountStack.push(valueCountStack.pop() + 1);
					}
				}

				@Override
				public void endElement(String uri, String localName,
						String qName) throws SAXException {
					if (qName.equalsIgnoreCase("PLUS")
							|| qName.equalsIgnoreCase("MINUS")
							|| qName.equalsIgnoreCase("TIMES")
							|| qName.equalsIgnoreCase("DIVIDEDBY")) {

						int numOfValues = 0;

						numOfValues = valueCountStack.pop();

						if (0 != numOfValues) {
							String operator = operators.pop();

							float result = (float) 0.0;
							for (int i = 0; i < numOfValues; i++) {
								float value = valuesStack.pop();

								if (operator.equalsIgnoreCase("PLUS")) {
									value += result;
								} else if (operator.equalsIgnoreCase("MINUS")) {
									value -= result;
								} else if (operator.equalsIgnoreCase("TIMES")) {
									if (i == 0) {
										result = value;
									} else {
										value *= result;
									}
								} else if (operator
										.equalsIgnoreCase("DIVIDEDBY")) {
									if (i == 0) {
										result = value;
									} else {
										try {
											value /= result;
										} catch (ArithmeticException ae) {
											throw new ArithmeticException(
													"err (ArithmeticException)");
										}
									}
								}

								result = value;
							}

							if (!valueCountStack.isEmpty())
								valueCountStack.push(valueCountStack.pop() + 1);
							else
								valueCountStack.push(1);

							valuesStack.push(result);
						}
					}
				}

				@Override
				public void characters(char ch[], int start, int length)
						throws SAXException {

				}
			});

			reader.parse(new InputSource(is));

			// pop result from stack.
			float finalResult = valuesStack.pop();
			return finalResult;

		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (EmptyStackException ese) {

		}

		System.out.println("    .... parsed o.k.");

		System.out.println("    .... computed result");

		return 0; // TODO: Return result or throw exception
	}
}
