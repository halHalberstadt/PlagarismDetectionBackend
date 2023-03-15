package com.plagarism.detect.domain;

public class QueryDTO {

    public String queryText;
    public boolean foundOnline;

    @Override
	public String toString() {
		return "QueryDTO [queryText=" + queryText + ", foundOnline=" + foundOnline + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
        QueryDTO other = (QueryDTO) obj;
		if (queryText != other.queryText)
			return false;
		if (foundOnline != other.foundOnline)
			return false;
		return true;
	}
}
