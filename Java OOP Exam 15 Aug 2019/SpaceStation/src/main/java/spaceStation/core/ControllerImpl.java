package spaceStation.core;

import spaceStation.models.astronauts.Astronaut;
import spaceStation.models.astronauts.Biologist;
import spaceStation.models.astronauts.Geodesist;
import spaceStation.models.astronauts.Meteorologist;
import spaceStation.models.mission.Mission;
import spaceStation.models.mission.MissionImpl;
import spaceStation.models.planets.Planet;
import spaceStation.models.planets.PlanetImpl;
import spaceStation.repositories.AstronautRepository;
import spaceStation.repositories.PlanetRepository;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static spaceStation.common.ConstantMessages.*;
import static spaceStation.common.ExceptionMessages.*;

public class ControllerImpl implements Controller {

    private AstronautRepository astronautRepository;
    private PlanetRepository planetRepository;
    private Mission mission;
    private int exploredPlanetCount;

    public ControllerImpl() {
        astronautRepository = new AstronautRepository();
        planetRepository = new PlanetRepository();
        mission = new MissionImpl();
        exploredPlanetCount = 0;
    }

    @Override
    public String addAstronaut(String type, String astronautName) {
        Astronaut astronaut;
        switch (type) {
            case "Biologist":
                astronaut = new Biologist(astronautName);
                break;
            case "Geodesist":
                astronaut = new Geodesist(astronautName);
                break;
            case "Meteorologist":
                astronaut = new Meteorologist(astronautName);
                break;
            default:
                throw new IllegalArgumentException(ASTRONAUT_INVALID_TYPE);
        }

        astronautRepository.add(astronaut);
        return String.format(ASTRONAUT_ADDED, type, astronautName);
    }

    @Override
    public String addPlanet(String planetName, String... items) {
        Planet planet = new PlanetImpl(planetName);
        planet.getItems().addAll(Arrays.asList(items));
        planetRepository.add(planet);
        return String.format(PLANET_ADDED, planetName);
    }

    @Override
    public String retireAstronaut(String astronautName) {
        Astronaut astronautToRetire = astronautRepository.findByName(astronautName);
        if (astronautToRetire == null) {
            throw new IllegalArgumentException(String.format(ASTRONAUT_DOES_NOT_EXIST, astronautName));
        }

        astronautRepository.remove(astronautToRetire);
        return String.format(ASTRONAUT_RETIRED, astronautName);
    }

    @Override
    public String explorePlanet(String planetName) {
        Planet planetToExplore = planetRepository.findByName(planetName);
        List<Astronaut> suitableAstronauts = astronautRepository.getModels().stream()
                .filter(astronaut -> astronaut.getOxygen() >= 60)
                .collect(Collectors.toList());

        if (suitableAstronauts.isEmpty()) {
            throw new IllegalArgumentException(PLANET_ASTRONAUTS_DOES_NOT_EXISTS);
        }
        mission.explore(planetToExplore, suitableAstronauts);
        long deadAstronauts = suitableAstronauts.stream()
                .filter(astronaut -> astronaut.getOxygen() == 0)
                .count();
        exploredPlanetCount++;
        return String.format(PLANET_EXPLORED, planetName, deadAstronauts);
    }

    @Override
    public String report() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format(REPORT_PLANET_EXPLORED, exploredPlanetCount)).append(String.format("%n"));
        sb.append(REPORT_ASTRONAUT_INFO).append(String.format("%n"));
        astronautRepository.getModels().forEach(astronaut -> {
            sb.append(String.format(REPORT_ASTRONAUT_NAME, astronaut.getName())).append(String.format("%n"));
            sb.append(String.format(REPORT_ASTRONAUT_OXYGEN, astronaut.getOxygen())).append(String.format("%n"));
            if (astronaut.getBag().getItems().isEmpty()) {
                sb.append(String.format(REPORT_ASTRONAUT_BAG_ITEMS, "none")).append(String.format("%n"));
            } else {
                sb.append(String.format(REPORT_ASTRONAUT_BAG_ITEMS,
                        astronaut.getBag().getItems().toString().replace("[", "")
                                .replace("]", ""))).append(String.format("%n"));
            }
        });

        return String.valueOf(sb);
    }
}
