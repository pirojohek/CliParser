package by.pirog.classifier;

import by.pirog.output.DataType;

public class LineClassifier {

    public DataType classify(String line){

        if (line == null){
            return DataType.STRING;
        }

        String s = line.trim();
        if (s.isEmpty()){
            return DataType.STRING;
        }

        boolean hasDot = s.contains(".");
        boolean hasExp = s.contains("E") || s.contains("e");

        if (!hasDot && !hasExp){
            try{
                Long.parseLong(s);
                return DataType.INTEGER;
            } catch (NumberFormatException e){

            }
        }

        try {
            double d = Double.parseDouble(s);
            if (!Double.isNaN(d) && !Double.isInfinite(d)) {
                return DataType.FLOAT;
            }
        } catch (NumberFormatException ignored) {
        }

        return DataType.STRING;
    }

}
