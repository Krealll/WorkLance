package com.krealll.worklance.model.validator;

public class BudgetValidator {

    private final static String BUDGET_PATTERN = "\\d{1,7}\\.\\d{1,8}";

    private BudgetValidator(){};

    public static boolean checkBudget(String budget){
        boolean result = false;
        if(budget!=null&&!budget.isEmpty()){
            result = budget.matches(BUDGET_PATTERN);
        }
        return result;
    }

}
