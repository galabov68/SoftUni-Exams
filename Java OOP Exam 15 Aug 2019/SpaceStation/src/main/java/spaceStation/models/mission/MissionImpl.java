package spaceStation.models.mission;

import spaceStation.models.astronauts.Astronaut;
import spaceStation.models.planets.Planet;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Deque;

public class MissionImpl implements Mission {

    @Override
    public void explore(Planet planet, Collection<Astronaut> astronauts) {
        Deque<String> deque = new ArrayDeque<>(planet.getItems());
        for (Astronaut astronaut : astronauts) {
            while (astronaut.canBreath()) {
                astronaut.breath();
                String item = deque.poll();
                planet.getItems().remove(item);
                astronaut.getBag().getItems().add(item);

                if (deque.isEmpty()) {
                    break;
                }
            }
            if (deque.isEmpty()) {
                break;
            }
        }
    }
}
