package models.harvest.tapir14;

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
			digester.addObjectCreate("*/dwrec:DarwinRecord", "models.Occurrence");
			
			// DWC 1.4 (Draft Standard) : core
			digester.addBeanPropertySetter("*/dwrec:DarwinRecord/dwcore:GlobalUniqueIdentifier", "institutionCode");
			digester.addBeanPropertySetter("*/dwrec:DarwinRecord/dwcore:DateLastModified", "modified");
			digester.addBeanPropertySetter("*/dwrec:DarwinRecord/dwcore:BasisOfRecord", "basisOfRecord");
			digester.addBeanPropertySetter("*/dwrec:DarwinRecord/dwcore:InstitutionCode", "institutionCode");
			digester.addBeanPropertySetter("*/dwrec:DarwinRecord/dwcore:CollectionCode", "collectionCode");
			digester.addBeanPropertySetter("*/dwrec:DarwinRecord/dwcore:CatalogNumber", "catalogNumber");
			digester.addBeanPropertySetter("*/dwrec:DarwinRecord/dwcore:InformationWithheld", "informationWithheld");
			digester.addBeanPropertySetter("*/dwrec:DarwinRecord/dwcore:Remarks", "occurrenceRemarks");
			digester.addBeanPropertySetter("*/dwrec:DarwinRecord/dwcore:ScientificName", "scientificName");
			digester.addBeanPropertySetter("*/dwrec:DarwinRecord/dwcore:HigherTaxon", "higherClassification");
			digester.addBeanPropertySetter("*/dwrec:DarwinRecord/dwcore:Kingdom", "kingdom");
			digester.addBeanPropertySetter("*/dwrec:DarwinRecord/dwcore:Phylum", "phylum");
			digester.addBeanPropertySetter("*/dwrec:DarwinRecord/dwcore:Class", "classs");
			digester.addBeanPropertySetter("*/dwrec:DarwinRecord/dwcore:Order", "orderr");
			digester.addBeanPropertySetter("*/dwrec:DarwinRecord/dwcore:Family", "family");
			digester.addBeanPropertySetter("*/dwrec:DarwinRecord/dwcore:Genus", "genus");
			digester.addBeanPropertySetter("*/dwrec:DarwinRecord/dwcore:SpecificEpithet", "specificEpithet");
			digester.addBeanPropertySetter("*/dwrec:DarwinRecord/dwcore:InfraspecificRank", "taxonRank");
			digester.addBeanPropertySetter("*/dwrec:DarwinRecord/dwcore:InfraspecificEpithet", "infraSpecificEpithet");
			digester.addBeanPropertySetter("*/dwrec:DarwinRecord/dwcore:AuthorYearOfScientificName", "scientificNameAuthorship");
			digester.addBeanPropertySetter("*/dwrec:DarwinRecord/dwcore:NomenclaturalCode", "nomenclaturalCode");
			digester.addBeanPropertySetter("*/dwrec:DarwinRecord/dwcore:IdentificationQualifier", "identificationQualifier");
			digester.addBeanPropertySetter("*/dwrec:DarwinRecord/dwcore:HigherGeography", "higherGeography");
			digester.addBeanPropertySetter("*/dwrec:DarwinRecord/dwcore:Continent", "continent");
			digester.addBeanPropertySetter("*/dwrec:DarwinRecord/dwcore:WaterBody", "waterBody");
			digester.addBeanPropertySetter("*/dwrec:DarwinRecord/dwcore:IslandGroup", "islandGroup");
			digester.addBeanPropertySetter("*/dwrec:DarwinRecord/dwcore:Island", "island");
			digester.addBeanPropertySetter("*/dwrec:DarwinRecord/dwcore:Country", "country");
			digester.addBeanPropertySetter("*/dwrec:DarwinRecord/dwcore:StateProvince", "stateProvince");
			digester.addBeanPropertySetter("*/dwrec:DarwinRecord/dwcore:County", "county");
			digester.addBeanPropertySetter("*/dwrec:DarwinRecord/dwcore:Locality", "locality");
			digester.addBeanPropertySetter("*/dwrec:DarwinRecord/dwcore:MinimumElevationInMeters", "minimumElevationInMeters");
			digester.addBeanPropertySetter("*/dwrec:DarwinRecord/dwcore:MaximumElevationInMeters", "maximumElevationInMeters");
			digester.addBeanPropertySetter("*/dwrec:DarwinRecord/dwcore:MinimumDepthInMeters", "minimumDepthInMeters");
			digester.addBeanPropertySetter("*/dwrec:DarwinRecord/dwcore:MaximumDepthInMeters", "maximumDepthInMeters");
			digester.addBeanPropertySetter("*/dwrec:DarwinRecord/dwcore:CollectingMethod", "samplingProtocol");
			digester.addBeanPropertySetter("*/dwrec:DarwinRecord/dwcore:ValidDistributionFlag", "establishmentMeans");
			// Problem : dwcore:EarliestDateCollected and dwcore:LatestDateCollected have the same field in database
			//digester.addBeanPropertySetter("*/dwrec:DarwinRecord/dwcore:EarliestDateCollected", "eventDate");
			digester.addBeanPropertySetter("*/dwrec:DarwinRecord/dwcore:LatestDateCollected", "eventDate");
			digester.addBeanPropertySetter("*/dwrec:DarwinRecord/dwcore:DayOfYear", "startDayOfYear");
			digester.addBeanPropertySetter("*/dwrec:DarwinRecord/dwcore:Collector", "recordedBy");
			digester.addBeanPropertySetter("*/dwrec:DarwinRecord/dwcore:Sex", "sex");
			digester.addBeanPropertySetter("*/dwrec:DarwinRecord/dwcore:LifeStage", "lifeStage");
			digester.addBeanPropertySetter("*/dwrec:DarwinRecord/dwcore:Attributes", "dynamicProperties");
			digester.addBeanPropertySetter("*/dwrec:DarwinRecord/dwcore:ImageURL", "associatedMedia");
			digester.addBeanPropertySetter("*/dwrec:DarwinRecord/dwcore:RelatedInformation", "referencess");
			
			// DWC 1.4 : Curatorial extension
			digester.addBeanPropertySetter("*/dwrec:DarwinRecord/dwcur:CatalogNumberNumeric:", "catalogNumber");
			digester.addBeanPropertySetter("*/dwrec:DarwinRecord/dwcur:IdentifiedBy", "identifiedBy");
			digester.addBeanPropertySetter("*/dwrec:DarwinRecord/dwcur:DateIdentified", "dateIdentified");
			digester.addBeanPropertySetter("*/dwrec:DarwinRecord/dwcur:CollectorNumber", "recordNumber");
			digester.addBeanPropertySetter("*/dwrec:DarwinRecord/dwcur:FieldNumber", "fieldNumber");
			digester.addBeanPropertySetter("*/dwrec:DarwinRecord/dwcur:FieldNotes", "fieldNotes");
			digester.addBeanPropertySetter("*/dwrec:DarwinRecord/dwcur:VerbatimCollectingDate", "verbatimEventDate");
			digester.addBeanPropertySetter("*/dwrec:DarwinRecord/dwcur:VerbatimElevation", "verbatimElevation");
			digester.addBeanPropertySetter("*/dwrec:DarwinRecord/dwcur:VerbatimDepth", "verbatimDepth");
			digester.addBeanPropertySetter("*/dwrec:DarwinRecord/dwcur:Preparations", "preparations");
			digester.addBeanPropertySetter("*/dwrec:DarwinRecord/dwcur:TypeStatus", "typeStatus");
			digester.addBeanPropertySetter("*/dwrec:DarwinRecord/dwcur:GenBankNumber", "associatedSequences");
			digester.addBeanPropertySetter("*/dwrec:DarwinRecord/dwcur:OtherCatalogNumbers", "otherCatalogNumbers");
			digester.addBeanPropertySetter("*/dwrec:DarwinRecord/dwcur:RelatedCatalogedItems", "associatedOccurrences");
			digester.addBeanPropertySetter("*/dwrec:DarwinRecord/dwcur:Disposition", "disposition");
			digester.addBeanPropertySetter("*/dwrec:DarwinRecord/dwcur:IndividualCount", "individualCount");
			
			// DWC 1.4 : Geospatial extension
			digester.addBeanPropertySetter("*/dwrec:DarwinRecord/dwgeo:DecimalLatitude", "decimalLatitude");
			digester.addBeanPropertySetter("*/dwrec:DarwinRecord/dwgeo:DecimalLongitude", "decimalLongitude");
			digester.addBeanPropertySetter("*/dwrec:DarwinRecord/dwgeo:GeodeticDatum", "geodeticDatum");
			digester.addBeanPropertySetter("*/dwrec:DarwinRecord/dwgeo:CoordinateUncertaintyInMeters", "coordinateUncertaintyInMeters");
			digester.addBeanPropertySetter("*/dwrec:DarwinRecord/dwgeo:PointRadiusSpatialFit", "pointRadiusSpatialFit");
			digester.addBeanPropertySetter("*/dwrec:DarwinRecord/dwgeo:VerbatimCoordinates", "verbatimCoordinates");
			digester.addBeanPropertySetter("*/dwrec:DarwinRecord/dwgeo:VerbatimLatitude", "verbatimLatitude");
			digester.addBeanPropertySetter("*/dwrec:DarwinRecord/dwgeo:VerbatimLongitude", "verbatimLongitude");
			digester.addBeanPropertySetter("*/dwrec:DarwinRecord/dwgeo:VerbatimCoordinateSystem", "verbatimCoordinateSystem");
			digester.addBeanPropertySetter("*/dwrec:DarwinRecord/dwgeo:GeoreferenceProtocol", "georeferenceProtocol");
			digester.addBeanPropertySetter("*/dwrec:DarwinRecord/dwgeo:GeoreferenceSources", "georeferenceSources");
			digester.addBeanPropertySetter("*/dwrec:DarwinRecord/dwgeo:GeoreferenceVerificationStatus", "georeferenceVerificationStatus");
			digester.addBeanPropertySetter("*/dwrec:DarwinRecord/dwgeo:GeoreferenceRemarks", "georeferenceRemarks");
			digester.addBeanPropertySetter("*/dwrec:DarwinRecord/dwgeo:FootprintWKT", "footprintWKT");
			digester.addBeanPropertySetter("*/dwrec:DarwinRecord/dwgeo:FootprintSpatialFit", "footprintSpatialFit");
			
			// DWC 1.4 : Paleontology extension
			//digester.addBeanPropertySetter("*/dwrec:DarwinRecord/dwpaleo:EarliestEonOrLowestEonothem", "earliestEonOrLowestEonothem");
			//digester.addBeanPropertySetter("*/dwrec:DarwinRecord/dwpaleo:LatestEonOrHighestEonothem", "latestEonOrHighestEonothem");
			//digester.addBeanPropertySetter("*/dwrec:DarwinRecord/dwpaleo:EarliestEraOrLowestErathem", "earliestEraOrLowestErathem");
			//digester.addBeanPropertySetter("*/dwrec:DarwinRecord/dwpaleo:LatestEraOrHighestErathem", "latestEraOrHighestErathem");
			//digester.addBeanPropertySetter("*/dwrec:DarwinRecord/dwpaleo:EarliestPeriodOrLowestSystem", "earliestPeriodOrLowestSystem");
			//digester.addBeanPropertySetter("*/dwrec:DarwinRecord/dwpaleo:LatestPeriodOrHighestSystem", "latestPeriodOrHighestSystem");
			//digester.addBeanPropertySetter("*/dwrec:DarwinRecord/dwpaleo:EarliestEpochOrLowestSeries", "earliestEpochOrLowestSeries");
			//digester.addBeanPropertySetter("*/dwrec:DarwinRecord/dwpaleo:LatestEpochOrHighestSeries", "latestEpochOrHighestSeries");
			//digester.addBeanPropertySetter("*/dwrec:DarwinRecord/dwpaleo:EarliestAgeOrLowestStage", "earliestAgeOrLowestStage");
			//digester.addBeanPropertySetter("*/dwrec:DarwinRecord/dwpaleo:LatestAgeOrHighestStage", "latestAgeOrHighestStage");
			//digester.addBeanPropertySetter("*/dwrec:DarwinRecord/dwpaleo:LowestBiostratigraphicZone", "lowestBiostratigraphicZone");
			//digester.addBeanPropertySetter("*/dwrec:DarwinRecord/dwpaleo:HighestBiostratigraphicZone", "highestBiostratigraphicZone");
			//digester.addBeanPropertySetter("*/dwrec:DarwinRecord/dwpaleo:LithostratigraphicTerms", "lithostratigraphicTerms");
			//digester.addBeanPropertySetter("*/dwrec:DarwinRecord/dwpaleo:Group", "groupp");
			//digester.addBeanPropertySetter("*/dwrec:DarwinRecord/dwpaleo:Formation", "formation");
			//digester.addBeanPropertySetter("*/dwrec:DarwinRecord/dwpaleo:Member", "member");
			//digester.addBeanPropertySetter("*/dwrec:DarwinRecord/dwpaleo:Bed", "bed");
			
			// This fields are not a part of DWC 1.4. What is that ?
			//digester.addBeanPropertySetter("*/dwrec:DarwinRecord/dwcore:SubspeciesPhylum", "infraSpecificEpithet");
			//digester.addBeanPropertySetter("*/dwrec:DarwinRecord/dwcore:ScientificNameAuthor", "scientificNameAuthorship");
			//digester.addBeanPropertySetter("*/dwrec:DarwinRecord/dwcore:YearIdentified", "dateIdentified");
			//digester.addBeanPropertySetter("*/dwrec:DarwinRecord/dwcore:MonthIdentified", "dateIdentified");
			//digester.addBeanPropertySetter("*/dwrec:DarwinRecord/dwcore:DayIdentified", "dateIdentified");
			//digester.addBeanPropertySetter("*/dwrec:DarwinRecord/dwcore:TypeStatus", "typeStatus");
			//digester.addBeanPropertySetter("*/dwrec:DarwinRecord/dwcore:FieldNumber", "fieldNumber");
			//digester.addBeanPropertySetter("*/dwrec:DarwinRecord/dwcore:CollectorNumber", "recordNumber");
			//digester.addBeanPropertySetter("*/dwrec:DarwinRecord/dwcore:YearCollected", "eventDate");
			//digester.addBeanPropertySetter("*/dwrec:DarwinRecord/dwcore:MonthCollected", "eventDate");
			//digester.addBeanPropertySetter("*/dwrec:DarwinRecord/dwcore:DayCollected", "eventDate");
			//digester.addBeanPropertySetter("*/dwrec:DarwinRecord/dwcore:JulianDay", "startDayOfYear");
			//digester.addBeanPropertySetter("*/dwrec:DarwinRecord/dwcore:TimeOfDay", "startTimeOfDay");
			//digester.addBeanPropertySetter("*/dwrec:DarwinRecord/dwcore:ContinentOcean", "waterBody");
			//digester.addBeanPropertySetter("*/dwrec:DarwinRecord/dwcore:CoordinatePrecision", "coordinatePrecision");
			//digester.addBeanPropertySetter("*/dwrec:DarwinRecord/dwcore:MinimumElevation", "minimumElevationInMeters");
			//digester.addBeanPropertySetter("*/dwrec:DarwinRecord/dwcore:MaximumElevation", "maximumElevationInMeters");
			//digester.addBeanPropertySetter("*/dwrec:DarwinRecord/dwcore:MinimumDepth", "minimumDepthInMeters");
			//digester.addBeanPropertySetter("*/dwrec:DarwinRecord/dwcore:MaximumDepth", "maximumDepthInMeters");
			//digester.addBeanPropertySetter("*/dwrec:DarwinRecord/dwcore:PreparationType", "preparations");
			//digester.addBeanPropertySetter("*/dwrec:DarwinRecord/dwcore:PreviousCatalogNumber", "otherCatalogNumbers");
			//digester.addBeanPropertySetter("*/dwrec:DarwinRecord/dwcore:RelationshipType", "relationshipOfResource");
			//digester.addBeanPropertySetter("*/dwrec:DarwinRecord/dwcore:RelatedCatalogItem", "associatedOccurrences");
			//digester.addBeanPropertySetter("*/dwrec:DarwinRecord/dwcore:Notes", "occurrenceRemarks");
			
			digester.addSetNext("*/dwrec:DarwinRecord", "add");
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