package dims.web.actionforms.location;
import org.apache.struts.action.ActionForm;

public class LocationListForm extends ActionForm  {
  private String locationTblPK; 
  private String locationID; 
  private String locationDescription; 
  private String locationCity; 
  private String locationState;
  private String locationCountry;
  private String locationZipcode;
  private String[] radSelect;
  private String[] hdnLocationName;
  private String hdnPageNumber;
  private String hdnPageCount;

  public void setLocationTblPK(String locationTblPK) {
    this.locationTblPK = locationTblPK;
  }

  public String getLocationTblPK() {
    return locationTblPK;
  }

  public void setLocationID(String locationID) {
    this.locationID = locationID;
  }

  public String getLocationID() {
    return locationID;
  }

  public void setLocationDescription(String locationDescription) {
    this.locationDescription = locationDescription;
  }

  public String getLocationDescription() {
    return locationDescription;
  }

  public void setLocationCity(String locationCity) {
    this.locationCity = locationCity;
  }

  public String getLocationCity() {
    return locationCity;
  }

  public void setLocationState(String locationState) {
    this.locationState = locationState;
  }

  public String getLocationState() {
    return locationState;
  }

  public void setLocationCountry(String locationCountry) {
    this.locationCountry = locationCountry;
  }

  public String getLocationCountry() {
    return locationCountry;
  }

  public void setLocationZipcode(String locationZipcode) {
    this.locationZipcode = locationZipcode;
  }

  public String getLocationZipcode() {
    return locationZipcode;
  }

  public void setRadSelect(String[] radSelect) {
    this.radSelect = radSelect;
  }

  public String[] getRadSelect() {
    return radSelect;
  }

  public void setHdnLocationName(String[] hdnLocationName) {
    this.hdnLocationName = hdnLocationName;
  }

  public String[] getHdnLocationName() {
    return hdnLocationName;
  }

  public void setHdnPageNumber(String hdnPageNumber) {
    this.hdnPageNumber = hdnPageNumber;
  }

  public String getHdnPageNumber() {
    return hdnPageNumber;
  }

  public void setHdnPageCount(String hdnPageCount) {
    this.hdnPageCount = hdnPageCount;
  }

  public String getHdnPageCount() {
    return hdnPageCount;
  }

}