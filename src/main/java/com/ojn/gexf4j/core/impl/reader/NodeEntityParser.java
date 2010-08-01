package com.ojn.gexf4j.core.impl.reader;

import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLStreamReader;

import com.ojn.gexf4j.core.Graph;
import com.ojn.gexf4j.core.Node;
import com.ojn.gexf4j.core.data.Attribute;
import com.ojn.gexf4j.core.data.AttributeValue;
import com.ojn.gexf4j.core.impl.NodeImpl;

public class NodeEntityParser extends AbstractEntityParser<Node> {
	private static final String ATTRIB_ID = "id";
	private static final String ATTRIB_LABEL = "label";
	private static final String ENTITY_ATTVALUES = "attvalues";
	
	private String id = "";
	private String label = "";
	private List<Attribute> attributes = null;
	private List<AttributeValue> attributeValues = null;
	private Graph graph = null;
	
	public NodeEntityParser(XMLStreamReader reader, List<Attribute> attributes, Graph graph) {
		super(reader);
		this.attributes = attributes;
		attributeValues = new ArrayList<AttributeValue>();
		this.graph = graph;
	}

	@Override
	protected Node newEntity() {
		return null;
	}

	@Override
	protected void onAttribute(String name, String value) {
		if (ATTRIB_ID.equalsIgnoreCase(name)) {
			id = value;
			
		} else if (ATTRIB_LABEL.equalsIgnoreCase(name)) {
			label = value;
		}
	}

	@Override
	protected void onStartElement(XMLStreamReader reader) {
		if (ENTITY_ATTVALUES.equalsIgnoreCase(reader.getLocalName())) {
			AttValuesEntityParser avep = new AttValuesEntityParser(reader, attributes);
			attributeValues = avep.getEntity();
		}
	}
	
	@Override
	public Node getEntity() {
		Node rv = new NodeImpl(id);
		
		rv.setLabel(label);
		rv.getAttributeValues().addAll(attributeValues);
		
		return rv;
	}

	@Override
	protected void onCharacters(XMLStreamReader reader) {
		// do nothing
	}

	@Override
	protected void onOther(XMLStreamReader reader, int eventType) {
		// do nothing
	}
}