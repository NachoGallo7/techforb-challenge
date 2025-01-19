package nacho.gallo.techforbchallenge.dtos.country;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
public class CountryDTO {

  private String flagIconUrl;
  private String name;

  @JsonGetter("flag_icon_url")
  public String getFlagIconUrl() {
    return flagIconUrl;
  }

  @JsonSetter("flags")
  public void setFlags(Map<String, String> flags) {
    this.flagIconUrl = flags.get("svg");
  }

  @JsonGetter("name")
  public String getName() {
    return name;
  }

  @JsonSetter("name")
  public void setName(Map<String, Object> nameMap) {
    this.name = (String) nameMap.get("common");
  }

}
