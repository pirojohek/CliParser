package by.pirog.classifier;

import by.pirog.output.DataType;

public class LineClassifier {
    public ClassificationResult classifyWithValue(String line){

        if (line == null){
            return new ClassificationResult(DataType.STRING, null);
        }

        String s = line.trim();
        if (s.isEmpty()){
            return new ClassificationResult(DataType.STRING, null);
        }

        boolean hasDot = s.contains(".");
        boolean hasExp = s.contains("E") || s.contains("e");

        if (!hasDot && !hasExp){
            try{
                Long value = Long.parseLong(s);
                return new ClassificationResult(DataType.INTEGER, value);
            } catch (NumberFormatException ignored){

            }
        }

        try {
            double d = Double.parseDouble(s);
            if (!Double.isNaN(d) && !Double.isInfinite(d)) {
                return new ClassificationResult(DataType.FLOAT, d);
            }
        } catch (NumberFormatException ignored) {
        }

        return new ClassificationResult(DataType.STRING, null);
    }

}
