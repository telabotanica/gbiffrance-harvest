package models.harvest.tapir2011;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.apache.commons.digester.Digester;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.log4j.Logger;
import models.Occurrence;
import org.xml.sax.SAXException;

public class ResponseToModelHandler {
	protected int count;
	protected boolean endOfRecords;
		
	public List<Occurrence> handleResponse(GZIPInputStream inputStream) throws IOException {
		List<Occurrence> results = new ArrayList<Occurrence>();
		try {
			// log the response
			Digester digester = new Digester();
			digester.setNamespaceAware(false);
			digester.setValidating(false);
			// Digester uses the class loader of its own class to find classes needed for object create rules. As Digester is bundled, the wrong class loader was used leading to this Exception.
			digester.setUseContextClassLoader(true);
			digester.push(results);
			digester.addObjectCreate("*/SimpleDarwinRecord", "models.Occurrence");
			
			// Record-level Terms
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dc:type", "typee");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dc:modified", "modified");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dc:language", "language");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dc:rights", "rights");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dc:rightsHolder", "rightsHolder");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dc:accessRights", "accessRights");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dc:bibliographicCitation", "bibliographicCitation");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dc:references", "references");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:institutionID", "institutionID");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:collectionID", "collectionID");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:datasetID", "datasetID");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:institutionCode", "institutionCode");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:collectionCode", "collectionCode");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:datasetName", "datasetName");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:ownerInstitutionCode", "ownerInstitutionCode");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:basisOfRecord", "basisOfRecord");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:informationWithheld", "informationWithheld");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:dataGeneralizations", "dataGeneralizations");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:dynamicProperties", "dynamicProperties");
			
			// Occurence
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:occurrenceID", "occurrenceID");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:catalogNumber", "catalogNumber");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:occurrenceRemarks", "occurrenceRemarks");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:recordNumber", "recordNumber");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:recordedBy", "recordedBy");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:individualID", "individualID");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:individualCount", "individualCount");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:sex", "sex");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:lifeStage", "lifeStage");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:reproductiveCondition", "reproductiveCondition");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:behavior", "behavior");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:establishmentMeans", "establishmentMeans");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:occurrenceStatus", "occurrenceStatus");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:preparations", "preparations");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:disposition", "disposition");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:otherCatalogNumbers", "otherCatalogNumbers");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:previousIdentifications", "previousIdentifications");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:associatedMedia", "associatedMedia");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:associatedReferences", "associatedReferences");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:associatedOccurrences", "associatedOccurrences");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:associatedSequences", "associatedSequences");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:associatedTaxa", "associatedTaxa");
			
			// Event
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:eventID", "eventID");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:samplingProtocol", "samplingProtocol");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:samplingEffort", "samplingEffort");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:eventDate", "eventDate");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:eventTime", "eventTime");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:startDayOfYear", "startDayOfYear");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:endDayOfYear", "endDayOfYear");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:year", "year");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:month", "month");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:day", "day");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:verbatimEventDate", "verbatimEventDate");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:habitat", "habitat");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:fieldNumber", "fieldNumber");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:fieldNotes", "fieldNotes");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:eventRemarks", "eventRemarks");
			
			// dcterms:Location
			//digester.addBeanPropertySetter("*/SimpleDarwinRecord/dc:Location", "");// No field in database
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:locationID", "locationID");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:higherGeographyID", "higherGeographyID");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:higherGeography", "higherGeography");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:continent", "continent");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:waterBody", "waterBody");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:islandGroup", "islandGroup");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:island", "island");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:country", "country");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:countryCode", "countryCode");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:stateProvince", "stateProvince");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:county", "county");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:municipality", "municipality");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:locality", "locality");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:verbatimLocality", "verbatimLocality");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:verbatimElevation", "verbatimElevation");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:minimumElevationInMeters", "minimumElevationInMeters");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:maximumElevationInMeters", "maximumElevationInMeters");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:verbatimDepth", "verbatimDepth");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:minimumDepthInMeters", "minimumDepthInMeters");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:maximumDepthInMeters", "maximumDepthInMeters");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:minimumDistanceAboveSurfaceInMeters", "minimumDistanceAboveSurfaceInMeters");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:maximumDistanceAboveSurfaceInMeters", "maximumDistanceAboveSurfaceInMeters");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:locationAccordingTo", "locationAccordingTo");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:locationRemarks", "locationRemarks");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:verbatimCoordinates", "verbatimCoordinates");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:verbatimLatitude", "verbatimLatitude");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:verbatimLongitude", "verbatimLongitude");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:verbatimCoordinateSystem", "verbatimCoordinateSystem");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:verbatimSRS", "verbatimSRS");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:decimalLatitude", "decimalLatitude");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:decimalLongitude", "decimalLongitude");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:geodeticDatum", "geodeticDatum");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:coordinateUncertaintyInMeters", "coordinateUncertaintyInMeters");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:coordinatePrecision", "coordinatePrecision");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:pointRadiusSpatialFit", "pointRadiusSpatialFit");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:footprintWKT", "footprintWKT");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:footprintSRS", "footprintSRS");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:footprintSpatialFit", "footprintSpatialFit");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:georeferencedBy", "georeferencedBy");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:georeferencedDate", "georeferencedDate");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:georeferenceProtocol", "georeferenceProtocol");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:georeferenceSources", "georeferenceSources");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:georeferenceVerificationStatus", "georeferenceVerificationStatus");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:georeferenceRemarks", "georeferenceRemarks");
			
			// GeologicalContext
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:geologicalContextID", "geologicalContextID");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:earliestEonOrLowestEonothem", "earliestEonOrLowestEonothem");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:latestEonOrHighestEonothem", "latestEonOrHighestEonothem");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:earliestEraOrLowestErathem", "earliestEraOrLowestErathem");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:latestEraOrHighestErathem", "latestEraOrHighestErathem");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:earliestPeriodOrLowestSystem", "earliestPeriodOrLowestSystem");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:latestPeriodOrHighestSystem", "latestPeriodOrHighestSystem");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:earliestEpochOrLowestSeries", "earliestEpochOrLowestSeries");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:latestEpochOrHighestSeries", "latestEpochOrHighestSeries");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:earliestAgeOrLowestStage", "earliestAgeOrLowestStage");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:latestAgeOrHighestStage", "latestAgeOrHighestStage");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:lowestBiostratigraphicZone", "lowestBiostratigraphicZone");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:highestBiostratigraphicZone", "highestBiostratigraphicZone");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:lithostratigraphicTerms", "lithostratigraphicTerms");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:group", "groupp");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:formation", "formation");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:member", "member");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:bed", "bed");
			
			// Identification
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:identificationID", "identificationID");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:identifiedBy", "identifiedBy");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:dateIdentified", "dateIdentified");
			// No field in database :
			//digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:identificationReferences", "identificationReferences");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:identificationVerificationStatus", "identificationVerificationStatus");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:identificationRemarks", "identificationRemarks");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:identificationQualifier", "identificationQualifier");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:typeStatus", "typeStatus");
			
			// Taxon
			// TODO : vérifier existence des champs dans la bdd
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:taxonID", "taxonID");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:scientificNameID", "scientificNameID");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:acceptedNameUsageID", "acceptedNameUsageID");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:parentNameUsageID", "parentNameUsageID");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:originalNameUsageID", "originalNameUsageID");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:nameAccordingToID", "nameAccordingToID");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:namePublishedInID", "namePublishedInID");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:taxonConceptID", "taxonConceptID");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:scientificName", "scientificName");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:acceptedNameUsage", "acceptedNameUsage");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:parentNameUsage", "parentNameUsage");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:originalNameUsage", "originalNameUsage");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:nameAccordingTo", "nameAccordingTo");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:namePublishedIn", "namePublishedIn");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:namePublishedInYear", "namePublishedInYear");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:higherClassification", "higherClassification");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:kingdom", "kingdom");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:phylum", "phylum");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:class", "classs");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:order", "orderr");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:family", "family");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:genus", "genus");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:subgenus", "subgenus");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:specificEpithet", "specificEpithet");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:infraspecificEpithet", "infraSpecificEpithet");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:taxonRank", "taxonRank");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:verbatimTaxonRank", "verbatimTaxonRank");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:scientificNameAuthorship", "scientificNameAuthorship");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:vernacularName", "vernacularName");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:nomenclaturalCode", "nomenclaturalCode");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:taxonomicStatus", "taxonomicStatus");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:nomenclaturalStatus", "nomenclaturalStatus");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:taxonRemarks", "taxonRemarks");
			
			// Auxiliary Terms : ResourceRelationship
			// TODO : vérifier existence des champs dans la bdd
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:resourceRelationshipID", "resourceRelationshipID");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:resourceID", "resourceID");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:relatedResourceID", "relatedResourceID");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:relationshipOfResource", "relationshipOfResource");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:relationshipAccordingTo", "relationshipAccordingTo");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:relationshipEstablishedDate", "relationshipEstablishedDate");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:relationshipRemarks", "relationshipRemarks");
			
			// Auxiliary Terms : MeasurementOrFact
			// TODO : vérifier existence des champs dans la bdd
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:measurementID", "measurementID");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:measurementType", "measurementType");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:measurementValue", "measurementValue");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:measurementAccuracy", "measurementAccuracy");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:measurementUnit", "measurementUnit");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:measurementDeterminedDate", "measurementDeterminedDate");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:measurementDeterminedBy", "measurementDeterminedBy");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:measurementMethod", "measurementMethod");
			digester.addBeanPropertySetter("*/SimpleDarwinRecord/dwc:measurementRemarks", "measurementRemarks");
			
			digester.addSetNext("*/SimpleDarwinRecord", "add");
			digester.parse(inputStream);
		} catch (SAXException e) {
			throw new IOException(e.getMessage());
		} finally {
			inputStream.close();
		}
		return results;
	}
	
	public void incrementCount(String code, String value) {
		if ("RECORD_COUNT".equals(code)) {
			try {
				count+=Integer.parseInt(value);
			} catch (NumberFormatException e) {
				
			}
		}
	}
		
	public void endOfRecords(String code, String value) {
		endOfRecords = false;
		if ("END_OF_RECORDS".equals(code)) {
			if ("TRUE".equalsIgnoreCase(value)) {
				endOfRecords = true;
			}
		}
	}

	public int getCount() {
		return count;
	}
	
	public void setCount(int count) {
		this.count = count;
	}
	
	public boolean isEndOfRecords() {
		return endOfRecords;
	}
	
	public void setEndOfRecords(boolean endOfRecords) {
		this.endOfRecords = endOfRecords;
	}
}