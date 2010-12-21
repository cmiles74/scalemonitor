package com.nervestaple.scalemonitor.form.datatable;

/**
 * Provides an interface and object can implement if the would like to be
 * notified of DataTableModel events.
 *
 * @author Christopher Miles
 * @version 1.0
 */
public interface DataTableModelListener {

    public void columnAdded( Object column );

    public void columnRemoved( Object column );

    public void rowAdded( Object[] row );

    public void rowRemoved( Object[] row );
}
