package tech.tablesaw.filtering.datetimes;

import tech.tablesaw.api.ColumnType;
import tech.tablesaw.api.DateColumn;
import tech.tablesaw.api.DateTimeColumn;
import tech.tablesaw.api.Table;
import tech.tablesaw.columns.Column;
import tech.tablesaw.columns.ColumnReference;
import tech.tablesaw.filtering.ColumnFilter;
import tech.tablesaw.util.Selection;

/**
 *
 */
public class IsInQ1 extends ColumnFilter {

    public IsInQ1(ColumnReference reference) {
        super(reference);
    }

    @Override
    public Selection apply(Table relation) {
        String name = columnReference().getColumnName();
        Column column = relation.column(name);
        ColumnType type = column.type();
        switch (type) {
            case LOCAL_DATE:
                DateColumn dateColumn = relation.dateColumn(name);
                return dateColumn.isInQ1();
            case LOCAL_DATE_TIME:
                DateTimeColumn dateTimeColumn = relation.dateTimeColumn(name);
                return dateTimeColumn.isInQ1();
            default:
                throw new UnsupportedOperationException("Columns of type " + type.name() + " do not support the operation "
                        + "isInQ1() ");
        }
    }
}
