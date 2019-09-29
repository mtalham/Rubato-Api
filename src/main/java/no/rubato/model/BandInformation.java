package no.rubato.model;

import javax.validation.constraints.NotBlank;

public class BandInformation {
  @NotBlank(message = "Name is required")
  private String name;

  @NotBlank(message = "About is required")
  private String about;

  @NotBlank(message = "Phone is required")
  private String phone;

  @NotBlank(message = "Price is required")
  private String price;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getAbout() {
    return about;
  }

  public void setAbout(String about) {
    this.about = about;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getPrice() {
    return price;
  }

  public void setPrice(String price) {
    this.price = price;
  }
}
