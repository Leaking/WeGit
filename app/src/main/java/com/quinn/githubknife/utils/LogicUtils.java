package com.quinn.githubknife.utils;

import com.quinn.httpknife.github.Branch;

import java.util.List;

/**
 * Created by Quinn on 9/25/15.
 */
public class LogicUtils {


    public static Branch defaultBranch(List<Branch> branches){
        for(Branch branch: branches){
            if(branch.getName().equals(Constants.MASTER))
                return branch;
        }
        return branches.get(0);
    }
}
