public class Promotion {
    double promotionRate = 0.0;

    public double pro(String gender, int age, double familyIncome, String marital) {
            if (gender.equals("female") && familyIncome <= 2000) {
                if (age > 40 && marital.equals("other")) {
                    promotionRate = 20.0 / 100;
                } else if (age > 40 && marital.equals("single")) {
                    promotionRate = 10.0 / 100;
                } else {
                    promotionRate = 5.0 / 100;
                }
            } else if (familyIncome <= 2000) {
                if (age > 40 && marital.equals("other")) {
                    promotionRate = 15.0 / 100;
                } else if (age > 40 && marital.equals("married")) {
                    promotionRate = 10.0 / 100;
                } else if (age > 40 && marital.equals("single")) {
                    promotionRate = 7.0 / 100;
                } else {
                    promotionRate = 5.0 / 100;
                }
            } else {
                promotionRate = 5.0 / 100;
            }
        return promotionRate;
    }
}
