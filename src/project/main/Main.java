package project.main;

import project.model.Model;
import project.model.AnimatorBuilder;
import project.model.swing.SwingAnimatorBuilder;
import project.model.text.TextAnimatorBuilder;
import project.ui.UI;
import project.ui.UIFactory;
import project.ui.UIMenuAction;
import project.ui.UIMenuBuilder;

/**
 * A static class to demonstrate the visualization aspect of
 * simulation.
 */
public class Main {
  private Main() {}
  public static void main(String[] args) {
	 UI ui = UIFactory.ui();
	 UIMenuBuilder menuBuilder = new UIMenuBuilder();
	 menuBuilder.add("Default", new UIMenuAction() {
			public void run() {
				System.exit(0);
			} });
	 menuBuilder.add("Run Simulation", new UIMenuAction() {
		public void run() {
		      Model m = new Model(new SwingAnimatorBuilder(), 2, 3);
		      m.run(500);
		      m.run(500);
		      m.dispose();
		}
	 });
	 menuBuilder.add("Exit", new UIMenuAction() {
		public void run() {
			System.exit(0);
		} });
	 ui.processMenu(menuBuilder.toUIMenu("Traffic Simulator"));
    System.exit(0);
  }
}

