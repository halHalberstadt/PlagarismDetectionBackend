package com.plagarism.detect.domain;

import java.util.ArrayList;

public class QueriesDTO {
    ArrayList<QueryDTO> listOfQueries;

	public ArrayList<QueryDTO> getListOfQueries() {
		return listOfQueries;
	}

    @Override
	public String toString() {
		return "QueriesDTO [ Queries=[" + listOfQueries + "] ]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
        QueriesDTO other = (QueriesDTO) obj;
        // check each query in list
		for (QueryDTO queryDTO : listOfQueries) {
            if(!listOfQueries.contains(queryDTO)){
                listOfQueries.remove(queryDTO);
                other.listOfQueries.remove(queryDTO);
            }
        }
		return false;
	}
}
