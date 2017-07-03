package com.dcare.po;

import java.util.Date;

public class Goods {
    private Integer id;

    private String name;

    private String imgUrl;

    private Float discountPrice;

    private Float price;

    private Integer sales;

    private Integer redirect;

    private String redirectId;

    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl == null ? null : imgUrl.trim();
    }

    public Float getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(Float discountPrice) {
        this.discountPrice = discountPrice;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Integer getSales() {
        return sales;
    }

    public void setSales(Integer sales) {
        this.sales = sales;
    }

    public Integer getRedirect() {
        return redirect;
    }

    public void setRedirect(Integer redirect) {
        this.redirect = redirect;
    }

    public String getRedirectId() {
        return redirectId;
    }

    public void setRedirectId(String redirectId) {
        this.redirectId = redirectId == null ? null : redirectId.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}