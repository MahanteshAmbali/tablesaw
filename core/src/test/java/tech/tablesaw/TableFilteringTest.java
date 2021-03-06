package tech.tablesaw;

import org.junit.Before;
import org.junit.Test;

import tech.tablesaw.api.ColumnType;
import tech.tablesaw.api.DateColumn;
import tech.tablesaw.api.IntColumn;
import tech.tablesaw.api.Table;
import tech.tablesaw.columns.packeddata.PackedLocalDate;
import tech.tablesaw.io.csv.CsvReader;

import java.time.LocalDate;

import static org.junit.Assert.*;
import static tech.tablesaw.api.QueryHelper.*;

/**
 * Tests for filtering on the T class
 */
public class TableFilteringTest {

    private final ColumnType[] types = {
            ColumnType.LOCAL_DATE,     // date of poll
            ColumnType.INTEGER,        // approval rating (pct)
            ColumnType.CATEGORY             // polling org
    };

    private Table table;

    @Before
    public void setUp() throws Exception {
        table = CsvReader.read(types, "../data/BushApproval.csv");
    }

    @Test
    public void testFilter1() {
        Table result = table.selectWhere(column("approval").isLessThan(70));
        IntColumn a = result.intColumn("approval");
        for (int v : a) {
            assertTrue(v < 70);
        }
    }

    @Test
    public void testFilter2() {
        Table result = table.selectWhere(column("date").isInApril());
        DateColumn d = result.dateColumn("date");
        for (LocalDate v : d) {
            assertTrue(PackedLocalDate.isInApril(PackedLocalDate.pack(v)));
        }
    }

    @Test
    public void testFilter3() {
        Table result = table.selectWhere(
                both(column("date").isInApril(),
                        column("approval").isGreaterThan(70)));

        DateColumn dates = result.dateColumn("date");
        IntColumn approval = result.intColumn("approval");
        for (int row : result) {
            assertTrue(PackedLocalDate.isInApril(dates.getInt(row)));
            assertTrue(approval.get(row) > 70);
        }
    }

    @Test
    public void testFilter4() {
        Table result =
                table.select("who", "approval")
                        .where(
                                and(column("date").isInApril(),
                                        column("approval").isGreaterThan(70)));
        assertEquals(2, result.columnCount());
        assertTrue(result.columnNames().contains("who"));
        assertTrue(result.columnNames().contains("approval"));
    }
}
