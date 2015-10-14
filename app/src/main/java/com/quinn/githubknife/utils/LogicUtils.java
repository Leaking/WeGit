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

    public static int parseStarredCount(String link){
        // <https://api.github.com/user/6383426/starred?per_page=1&page=86>;rel="last"
        int begin = link.lastIndexOf("page") + 5;
        int end = link.lastIndexOf(">");
        return Integer.parseInt(link.substring(begin, end));
    }
}
