package bck_instapic.configuration.typehandler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.postgresql.util.PGobject;

import java.sql.*;

public class VectorTypeHandler extends BaseTypeHandler<float[]> {

    private static final String VECTOR_TYPE = "vector";

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, float[] parameter, JdbcType jdbcType) throws SQLException {
        // Convert float[] to a comma-separated string
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        for (int j = 0; j < parameter.length; j++) {
            sb.append(parameter[j]);
            if (j < parameter.length - 1) {
                sb.append(',');
            }
        }
        sb.append(']');
        String vectorString = sb.toString();

        // Create a PGobject to represent the vector
        PGobject vectorObject = new PGobject();
        vectorObject.setType(VECTOR_TYPE);
        vectorObject.setValue(vectorString);

        ps.setObject(i, vectorObject);
    }

    @Override
    public float[] getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return parseVector(rs.getObject(columnName));
    }

    @Override
    public float[] getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return parseVector(rs.getObject(columnIndex));
    }

    @Override
    public float[] getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return parseVector(cs.getObject(columnIndex));
    }

    /**
     * Parses the PostgreSQL vector object to a float array.
     *
     * @param object the object retrieved from the ResultSet or CallableStatement
     * @return the float array representing the vector
     * @throws SQLException if an error occurs during parsing
     */
    private float[] parseVector(Object object) throws SQLException {
        if (object == null) {
            return null;
        }

        if (!(object instanceof String)) {
            throw new SQLException("Unexpected type for vector: " + object.getClass().getName());
        }

        String vectorStr = (String) object;
        // Remove the square brackets
        vectorStr = vectorStr.substring(1, vectorStr.length() - 1);
        String[] parts = vectorStr.split(",");
        float[] vector = new float[parts.length];
        for (int i = 0; i < parts.length; i++) {
            vector[i] = Float.parseFloat(parts[i].trim());
        }
        return vector;
    }
}