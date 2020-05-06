package sample.source.map;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

public class Path {

    private List<Coordinate> path;


    public Path() {
    }

    public Path(List<Coordinate> path) {
        this.path = path;
    }

    public List<Coordinate> getPath() {
        return path;
    }

    /** Distance between two coordinates **/
    private double getDistanceBetweenCoordinates(Coordinate a, Coordinate b) {
        // Everything ok
        return Math.sqrt(Math.pow(a.getX() - b.getX(), 2) + Math.pow(a.getY() - b.getY(), 2));

    }


    /** Calculate new coordinates after move **/
    public Coordinate getCoordinateByDistance(double distance, Path pathOfAutobus, Coordinate startOfAutobus, Autobus autobus) {

        // Length of all ways in path
        double length = 0;
        //index of path on witch autobus started
        int j = pathOfAutobus.getPath().indexOf(startOfAutobus);
        //set first street on witch is autobus
        if(autobus.getAutobusIsOnStreet() == null){
            //get autobuses line
            Line lineOfAutobus = autobus.getLine();
            //get list of street on that line
            List<Street> listOfStreetsInLine = lineOfAutobus.getStreetList();
            //for each street
            for (Street str: listOfStreetsInLine) {
                //get start and end of street
                Coordinate streetStart = str.begin();
                Coordinate streetEnd = str.end();
                //street contains 3 coordinates
                if(str.getCoordinates().size() == 3 && j < path.size() - 2){
                    //autobus started on middle of street
                    if (str.getCoordinates().get(1).equals(autobus.getStartOfAutobus())){
                        if (streetStart.equals(path.get(j-1)) && streetEnd.equals(path.get(j+1))){
                            autobus.setAutobusIsOnStreet(str);
                            break;
                        }
                        else if(streetStart.equals(path.get(j+1)) && streetEnd.equals(path.get(j-1))){
                            autobus.setAutobusIsOnStreet(str);
                            break;
                        }
                    }
                    // autobus started on edge of street
                    else{
                        if (streetStart.equals(path.get(j)) && streetEnd.equals(path.get(j+2))){
                            autobus.setAutobusIsOnStreet(str);
                            break;
                        }
                        else if(streetStart.equals(path.get(j+2)) && streetEnd.equals(path.get(j))){
                            autobus.setAutobusIsOnStreet(str);
                            break;
                        }
                    }

                }
                //street contains 2 coordinates
                else{
                    if (streetStart.equals(path.get(j)) && streetEnd.equals(path.get(j+1))){
                        autobus.setAutobusIsOnStreet(str);
                        break;
                    }
                    else if(streetStart.equals(path.get(j+1)) && streetEnd.equals(path.get(j))){
                        autobus.setAutobusIsOnStreet(str);
                        break;
                    }
                }

            }
        }

        // Initialize coordinates two null (get coordinates with function getDistBetweenCoor)
        Coordinate a = null;
        Coordinate b = null;

        // Iterate over all coordinates
        for(int i=j; i< path.size() - 1; i++) {
            a = path.get(i);
            b = path.get(i + 1);

            if(autobus.flagForChangeAutobusStreet){
                //get autobuses line
                Line lineOfAutobus = autobus.getLine();
                //get list of street on that line
                List<Street> listOfStreetsInLine = lineOfAutobus.getStreetList();
                //for each street
                for (Street str: listOfStreetsInLine) {
                    //get start and end of street
                    Coordinate streetStart = str.begin();
                    Coordinate streetEnd = str.end();
                    //street contains 3 coordinates
                    if(str.getCoordinates().size() == 3 && i < path.size() - 2){
                        //autobus started on middle of street
                        if (str.getCoordinates().get(1).equals(autobus.getStartOfAutobus())){
                            if (streetStart.equals(path.get(i-1)) && streetEnd.equals(path.get(i+1))){
                                autobus.setAutobusIsOnStreet(str);
                                break;
                            }
                            else if(streetStart.equals(path.get(i+1)) && streetEnd.equals(path.get(i-1))){
                                autobus.setAutobusIsOnStreet(str);
                                break;
                            }
                        }
                        // autobus started on edge of street
                        else{
                            if (streetStart.equals(path.get(i)) && streetEnd.equals(path.get(i+2))){
                                autobus.setAutobusIsOnStreet(str);
                                break;
                            }
                            else if(streetStart.equals(path.get(i+2)) && streetEnd.equals(path.get(i))){
                                autobus.setAutobusIsOnStreet(str);
                                break;
                            }
                        }

                    }
                    //street contains 2 coordinates
                    else{
                        if (streetStart.equals(path.get(i)) && streetEnd.equals(path.get(i+1))){
                            autobus.setAutobusIsOnStreet(str);
                            break;
                        }
                        else if(streetStart.equals(path.get(i+1)) && streetEnd.equals(path.get(i))){
                            autobus.setAutobusIsOnStreet(str);
                            break;
                        }
                    }
                }
            }

            autobus.flagForChangeAutobusStreet = false;

            if(length + (getDistanceBetweenCoordinates(a, b)) >= distance) {
                break;
            }

            autobus.flagForChangeAutobusStreet = true;

            // Add path between 2 coords to final way
            length += getDistanceBetweenCoordinates(a, b);
        }

        if (a == null || b == null) {
            return null;
        }

        
        double driven = (distance - length) / (getDistanceBetweenCoordinates(a, b));
        return new Coordinate(a.getX() + (b.getX() - a.getX()) * driven, a.getY() + (b.getY() - a.getY()) * driven);
    }


    // Return size of all the way
    @JsonIgnore
    public double getPathSize() {
        double size = 0;
        for(int i=0; i< path.size() - 1; i++) {
            size += getDistanceBetweenCoordinates(path.get(i), path.get(i+1));
        }
        return size;
    }
}
