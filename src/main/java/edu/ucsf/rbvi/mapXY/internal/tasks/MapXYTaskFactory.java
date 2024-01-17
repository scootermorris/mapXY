package edu.ucsf.rbvi.mapXY.internal.tasks;

import org.cytoscape.service.util.CyServiceRegistrar;
import org.cytoscape.task.AbstractNetworkViewTaskFactory;
import org.cytoscape.task.AbstractNetworkViewTask;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.work.TaskIterator;


public class MapXYTaskFactory extends AbstractNetworkViewTaskFactory {
				final CyServiceRegistrar registrar;
				public MapXYTaskFactory(final CyServiceRegistrar registrar) {
								this.registrar = registrar;
				}

				public TaskIterator createTaskIterator(CyNetworkView networkView) {
								return new TaskIterator(new MapXYTask(networkView));
				}
}

