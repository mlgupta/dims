package dims.web.beans.location;

public class LocationListBean  {
  private String locationTblPK; 
  private String locationID;
  private String locationDescription;
  private String locationCity;
  private String locationState;
  private String locationCountry;
  private String locationZipcode;
  public LocationListBean() {  
  }

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
}