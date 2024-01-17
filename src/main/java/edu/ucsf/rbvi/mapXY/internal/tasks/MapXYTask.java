package edu.ucsf.rbvi.mapXY.internal.tasks;

import org.cytoscape.model.CyColumn;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;
import org.cytoscape.model.CyTable;
import org.cytoscape.service.util.CyServiceRegistrar;
import org.cytoscape.task.AbstractNetworkViewTaskFactory;
import org.cytoscape.task.AbstractNetworkViewTask;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.View;
import org.cytoscape.view.presentation.property.BasicVisualLexicon;
import org.cytoscape.work.TaskMonitor;


public class MapXYTask extends AbstractNetworkViewTask {
	final CyNetworkView view;
	public MapXYTask(final CyNetworkView view) {
		super(view);
		this.view = view;
	}

	public void run(TaskMonitor monitor) {
		CyNetwork net = view.getModel();
		CyTable nodeTable = net.getDefaultNodeTable();

		String err = createIfNeeded(nodeTable, "X", Double.class);
		if (err != null) {
			monitor.showMessage(TaskMonitor.Level.ERROR, err);
			return;
		}
		err = createIfNeeded(nodeTable, "Y", Double.class);
		if (err != null) {
			monitor.showMessage(TaskMonitor.Level.ERROR, err);
			return;
		}

		for (View<CyNode> nodeView: view.getNodeViews()) {
			double X = nodeView.getVisualProperty(BasicVisualLexicon.NODE_X_LOCATION);
			double Y = nodeView.getVisualProperty(BasicVisualLexicon.NODE_Y_LOCATION);
			CyNode node = nodeView.getModel();
			nodeTable.getRow(node.getSUID()).set("X", X);
			nodeTable.getRow(node.getSUID()).set("Y", Y);
		}

	}

	private String createIfNeeded(CyTable table, String column, Class clazz) {
		CyColumn col = table.getColumn(column);
		if (col != null) {
			if (col.getType() != clazz) {
				return "Error: column "+column+" already exists";
			}
		} else {
			table.createColumn(null, column, clazz, false);
		}
		return null;
	}
}

