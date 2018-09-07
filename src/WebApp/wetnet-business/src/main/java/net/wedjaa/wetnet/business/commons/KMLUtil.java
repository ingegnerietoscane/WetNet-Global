/**
 * 
 */
package net.wedjaa.wetnet.business.commons;

import java.util.List;

import de.micromata.opengis.kml.v_2_2_0.Boundary;
import de.micromata.opengis.kml.v_2_2_0.Coordinate;
import de.micromata.opengis.kml.v_2_2_0.Document;
import de.micromata.opengis.kml.v_2_2_0.Feature;
import de.micromata.opengis.kml.v_2_2_0.Folder;
import de.micromata.opengis.kml.v_2_2_0.Geometry;
import de.micromata.opengis.kml.v_2_2_0.LinearRing;
import de.micromata.opengis.kml.v_2_2_0.Placemark;
import de.micromata.opengis.kml.v_2_2_0.Polygon;

/**
 * @author massimo ricci
 *
 */
public class KMLUtil {

    /**
     * Ritorna la lista dei placemarks
     * @param featureDocument
     * @return
     */
    public static List<Feature> getPlacemarkList(Feature featureDocument){
        
        if(featureDocument != null) {
            if(featureDocument instanceof Document) {
                Document document = (Document) featureDocument;
                List<Feature> folderList = document.getFeature();
                for(Feature featureFolder : folderList) {
                    if(featureFolder instanceof Folder) {
                        Folder folder = (Folder) featureFolder;
                        List<Feature> placemarkList = folder.getFeature();
                        return placemarkList;
                    }
                }
            }
        }
        
        return null;
    }
    
    public static void printCoordinates(Feature featureDocument){
        if(featureDocument != null) {
            if(featureDocument instanceof Document) {
                Document document = (Document) featureDocument;
                List<Feature> folderList = document.getFeature();
                for(Feature featureFolder : folderList) {
                    if(featureFolder instanceof Folder) {
                        Folder folder = (Folder) featureFolder;
                        List<Feature> placemarkList = folder.getFeature();
                        for(Feature placemarkFeature : placemarkList) {
                            if(placemarkFeature instanceof Placemark) {
                                Placemark placemark = (Placemark) placemarkFeature;
                                Geometry geometry = placemark.getGeometry();
                                parseGeometry(geometry);
                            }
                        }
                    }
                }
            }
        }
    }
    
    private static void parseGeometry(Geometry geometry) {
        if(geometry != null) {
            if(geometry instanceof Polygon) {
                Polygon polygon = (Polygon) geometry;
                Boundary outerBoundaryIs = polygon.getOuterBoundaryIs();
                if(outerBoundaryIs != null) {
                    LinearRing linearRing = outerBoundaryIs.getLinearRing();
                    if(linearRing != null) {
                        List<Coordinate> coordinates = linearRing.getCoordinates();
                        if(coordinates != null) {
                            for(Coordinate coordinate : coordinates) {
                                parseCoordinate(coordinate);
                            }
                        }
                    }
                }
            }
        }
    }
    
    private static void parseCoordinate(Coordinate coordinate) {
        if(coordinate != null) {
            System.out.println("Longitude: " +  coordinate.getLongitude());
            System.out.println("Latitude : " +  coordinate.getLatitude());
            System.out.println("Altitude : " +  coordinate.getAltitude());
            System.out.println("");
        }
    }
}
