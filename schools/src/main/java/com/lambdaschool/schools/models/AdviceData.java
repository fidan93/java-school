package com.lambdaschool.schools.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AdviceData
{
  private Map<String,String> slip;

    public Map<String, String> getSlip()
    {
        return slip;
    }

    public void setSlip(Map<String, String> slip)
    {
        this.slip = slip;
    }

    @Override
    public String toString()
    {
        return "AdviceData{" +
            "slip=" + slip +
            '}';
    }
}
