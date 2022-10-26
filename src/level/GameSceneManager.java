package level;

import java.util.ArrayList;

public class GameSceneManager {
	private ArrayList<Scene> scenes; 
	private int currentSceneIndex = -1;

	public GameSceneManager() {
		scenes = new ArrayList<Scene>();
	} 

	public Scene getCurrentScene() {
		if(currentSceneIndex >= 0)
			return scenes.get(currentSceneIndex);
		return null;
	}

	public void pushScene(Scene scene) {
		scenes.add(scene);
		++currentSceneIndex;
	}

	public void popScene() {
		scenes.remove(currentSceneIndex--);
	}
}
