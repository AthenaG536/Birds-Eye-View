package com.example.birdseyeview;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Arrays;

public class bird {

    public String scientificName, commonName, speciesCode, category, taxonOrder, commonNameCodes, scientificNameCodes, bandingCodes, order, familyCommonName, familyScientificName, reportAs, extinct, extinctYear;
    public ArrayList<ArrayList<String>>  birdInfo;
    public ArrayList<String> tempDetail;

    public bird() {
        setTempBirdInfo();
    }

    public bird(final String scientificName, final String commonName, final String speciesCode, final String familyCommonName) {

        setTempBirdInfo();
        this.setScientificName(scientificName);
        this.setCommonName(commonName);
        this.setSpeciesCode(speciesCode);
        this.setFamilyCommonName(familyCommonName);
    }

    public bird(String scientificName, String commonName, String speciesCode, String category, String taxonOrder, String commonNameCodes, String scientificNameCodes, String bandingCodes, String order, String familyCommonName, String familyScientificName, String reportAs, String extinct, String extinctYear) {


        setTempBirdInfo();
        this.setScientificName(scientificName);
        this.setCommonName(commonName);
        this.setSpeciesCode(speciesCode);
        this.setFamilyCommonName(familyCommonName);
        this.setCategory(category);
        this.setTaxonOrder(taxonOrder);
        this.setCommonNameCodes(commonNameCodes);
        this.setScientificNameCodes(scientificNameCodes);
        this.setBandingCodes(bandingCodes);
        this.setOrder(order);
        this.setFamilyScientificName(familyScientificName);
        this.setReportAs(reportAs);
        this.setExtinct(extinct);
        this.setExtinctYear(extinctYear);
    }

    public ArrayList<String> setTitleInfoList(String tempTitle, String tempInfo){
        return new ArrayList<String>(Arrays.asList(new String[]{tempTitle, tempInfo}));

    }

    public void setTempBirdInfo() {
        ArrayList<ArrayList<String>> tempbirdInfo = new ArrayList<ArrayList<String>>(new ArrayList<ArrayList<String>>());
        tempbirdInfo.add(new ArrayList<String>(Arrays.asList(new String[]{"Scientific Name: ", null})));
        tempbirdInfo.add(new ArrayList<String>(Arrays.asList(new String[]{"Common Name: ", null})));
        tempbirdInfo.add(new ArrayList<String>(Arrays.asList(new String[]{"Species Code: ", null})));
        tempbirdInfo.add(new ArrayList<String>(Arrays.asList(new String[]{"Category: ",null})));
        tempbirdInfo.add(new ArrayList<String>(Arrays.asList(new String[]{"Taxonomy Order: ", null})));

        tempbirdInfo.add(new ArrayList<String>(Arrays.asList(new String[]{"Common Name Codes: ", null})));
        tempbirdInfo.add(new ArrayList<String>(Arrays.asList(new String[]{"Scientific Name Codes: ", null})));
        tempbirdInfo.add(new ArrayList<String>(Arrays.asList(new String[]{"Banding Codes: ",null})));


        tempbirdInfo.add(new ArrayList<String>(Arrays.asList(new String[]{"Order: ", null})));
        tempbirdInfo.add(new ArrayList<String>(Arrays.asList(new String[]{"Family Common Name: ", null})));
        tempbirdInfo.add(new ArrayList<String>(Arrays.asList(new String[]{"Family Scientific Name: ", null})));

        tempbirdInfo.add(new ArrayList<String>(Arrays.asList(new String[]{"Report as: ", null})));
        tempbirdInfo.add(new ArrayList<String>(Arrays.asList(new String[]{"Extinct: ", null})));
        tempbirdInfo.add(new ArrayList<String>(Arrays.asList(new String[]{"Year of Extinction: ", null})));
        this.birdInfo = tempbirdInfo;
    }

    public String getScientificName() {
        return scientificName;
    }

    public void setScientificName(String scientificName) {
        birdInfo.set(0, setTitleInfoList(birdInfo.get(0).get(0),scientificName));
        this.scientificName = scientificName;
    }

    public String getCommonName() {
        return commonName;
    }

    public void setCommonName(String commonName) {
        birdInfo.set(1, setTitleInfoList(birdInfo.get(1).get(0),commonName));
        this.commonName = commonName;
    }

    public String getSpeciesCode() {
        return speciesCode;
    }

    public void setSpeciesCode(String speciesCode) {
        birdInfo.set(2, setTitleInfoList(birdInfo.get(2).get(0),speciesCode));
        this.speciesCode = speciesCode;
    }
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        birdInfo.set(3, setTitleInfoList(birdInfo.get(3).get(0),category));
        this.category = category;
    }

    public String getTaxonOrder() {
        return taxonOrder;
    }

    public void setTaxonOrder(String taxonOrder) {
        birdInfo.set(4, setTitleInfoList(birdInfo.get(4).get(0),taxonOrder));
        this.taxonOrder = taxonOrder;
    }

    public String getCommonNameCodes() {
        return commonNameCodes;
    }

    public void setCommonNameCodes(String commonNameCodes) {
        birdInfo.set(5, setTitleInfoList(birdInfo.get(5).get(0),commonNameCodes));
        this.commonNameCodes = commonNameCodes;
    }
    public String getScientificNameCodes() {
        return scientificNameCodes;
    }

    public void setScientificNameCodes(String scientificNameCodes) {
        birdInfo.set(6, setTitleInfoList(birdInfo.get(6).get(0),scientificNameCodes));
        this.scientificNameCodes = scientificNameCodes;
    }

    public String getBandingCodes() {
        return bandingCodes;
    }

    public void setBandingCodes(String bandingCodes) {
        birdInfo.set(7, setTitleInfoList(birdInfo.get(7).get(0),bandingCodes));
        this.bandingCodes = bandingCodes;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        birdInfo.set(8, setTitleInfoList(birdInfo.get(8).get(0),order));
        this.order = order;
    }

    public String getFamilyCommonName() {
        return familyCommonName;
    }

    public void setFamilyCommonName(String familyCommonName) {
        birdInfo.set(9, setTitleInfoList(birdInfo.get(9).get(0),familyCommonName));
        this.familyCommonName = familyCommonName;
    }

    public String getFamilyScientificName() {
        return familyScientificName;
    }

    public void setFamilyScientificName(String familyScientificName) {
        birdInfo.set(10, setTitleInfoList(birdInfo.get(10).get(0),familyScientificName));
        this.familyScientificName = familyScientificName;
    }

    public String getReportAs() {
        return reportAs;
    }

    public void setReportAs(String reportAs) {
        birdInfo.set(11, setTitleInfoList(birdInfo.get(11).get(0),reportAs));
        this.reportAs = reportAs;
    }

    public String getExtinct() {
        return extinct;
    }

    public void setExtinct(String extinct) {
        birdInfo.set(12, setTitleInfoList(birdInfo.get(12).get(0),extinct));
        this.extinct = extinct;
    }

    public String getExtinctYear() {
        return extinctYear;
    }

    public void setExtinctYear(String extinctYear) {
        birdInfo.set(13, setTitleInfoList(birdInfo.get(13).get(0),extinctYear));
        this.extinctYear = extinctYear;
    }

    @NonNull
    @Override
    public String toString() {
        String tmpString = "";
        for (ArrayList<String> detail:birdInfo) {
            if(!detail.get(1).isEmpty()){
                tmpString += "\t" + detail.get(0) + detail.get(1);
            }
        }


        return tmpString;
    }

}
