package org.project.travel_agency.Repository.DB_Repository;

import org.project.travel_agency.Repository.RepoInterface;
import org.project.travel_agency.Utility.DB_Utils;
public class Repo_Account implements RepoInterface {
    private final DB_Utils dbUtils;

    public Repo_Account(DB_Utils dbUtils) {
        this.dbUtils = dbUtils;
    }
}
