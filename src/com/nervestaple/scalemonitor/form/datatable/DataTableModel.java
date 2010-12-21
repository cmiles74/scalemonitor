package com.nervestaple.scalemonitor.form.datatable;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *  Provides a data model for the DataTable.
 *
 * @author Christopher Miles
 * @version 1.0
 */
public class DataTableModel {

    /**
     * List of column headers.
     */
    private List columnHeaders;

    /**
     * List of rows.
     */
    private List dataRows;

    /**
     * List of listeners.
     */
    private List listeners;

    /**
     * Property change support.
     */
    private PropertyChangeSupport propertychangesupport;

    /**
     * Creates a new DataTableModel
     */
    public DataTableModel() {

        columnHeaders = new ArrayList();
        dataRows = new ArrayList();
        listeners = new ArrayList();
        propertychangesupport = new PropertyChangeSupport( this );
    }

    public Object[] getColumnHeadersArray() {

        Object[] headers = ( Object[] ) columnHeaders.toArray( new Object[ columnHeaders.size() ] );

        return( headers );
    }

    public Object[][] getDataRowsArray() {

        Object[][] arrayData = new Object[ 0 ][ 0 ];

        if( dataRows.size() > 0 ) {

            // create an array to hold the data
            arrayData = new Object[ dataRows.size() ][ ( ( Object[] ) dataRows.get( 0 ) ).length ];

            // get the rows as an array
            Object[] arrayRows = ( Object[] ) dataRows.toArray( new Object[ dataRows.size() ] );

            // loop through the array and add to our large data array
            for ( int index = 0; index < arrayRows.length; index++ ) {

                Object[] row = ( Object[] ) arrayRows[ index ];

                // loop through the row array
                for ( int indexRow = 0; indexRow < row.length; indexRow++ ) {

                    arrayData[ index ][ indexRow ] = row[ indexRow ];
                }
            }
        }

        return( arrayData );
    }

    public void addDataRow( Object[] row ) {

        dataRows.add( row );

        notifyRowAdded( row );
    }

    public void removeDataRow( Object[] row ) {

        dataRows.remove( row );

        notifyRowRemoved( row );
    }

    public void addColumn( Object column ) {

        columnHeaders.add( column );

        notifyColumnAdded( column );
    }

    public void removeColumn( Object column ) {

        columnHeaders.remove( column );

        notifyColumnRemoved( column );
    }

    public void addDataTableModelListener( DataTableModelListener listener ) {

        if( !listeners.contains( listener ) ) {
            listeners.add( listener );
        }
    }

    public void removeDataTableModelListener( DataTableModelListener listener ) {

        listeners.remove( listener );
    }

    public DataTableModelListener[] getDataTableModelListeners() {

        DataTableModelListener[] arrayListeners =
                ( DataTableModelListener[] ) listeners.toArray( new DataTableModelListener[ listeners.size() ] );

        return( arrayListeners );
    }

    public List getDataRows() {
        return dataRows;
    }

    public void setDataRows( List dataRows ) {
        List valueOld = this.dataRows;
        this.dataRows = dataRows;
        propertychangesupport.firePropertyChange( "dataRows", valueOld, Collections.unmodifiableList( dataRows ) );
    }

    public List getColumnHeaders() {
        return columnHeaders;
    }

    public void setColumnHeaders( List columnHeaders ) {
        List valueOld = this.columnHeaders;
        this.columnHeaders = columnHeaders;
        propertychangesupport.firePropertyChange( "columnHeaders", valueOld,
                Collections.unmodifiableList( columnHeaders ) );
    }

    // property change support methods

    public void addPropertyChangeListener( PropertyChangeListener listener ) {
        propertychangesupport.addPropertyChangeListener( listener );
    }

    public void removePropertyChangeListener( PropertyChangeListener listener ) {
        propertychangesupport.removePropertyChangeListener( listener );
    }

    public void addPropertyChangeListener( String property, PropertyChangeListener listener ) {
        propertychangesupport.addPropertyChangeListener( property, listener );
    }

    public void removePropertyChangeListener( String property, PropertyChangeListener listener ) {
        propertychangesupport.removePropertyChangeListener( property, listener );
    }

    // private methods

    private void notifyRowAdded( Object[] row ) {

        DataTableModelListener[] arrayListener = getDataTableModelListeners();

        for( int index = 0; index < arrayListener.length; index++ ) {

            arrayListener[ index ].rowAdded( row );
        }
    }

    private void notifyRowRemoved( Object[] row ) {

        DataTableModelListener[] arrayListener = getDataTableModelListeners();

        for ( int index = 0; index < arrayListener.length; index++ ) {

            arrayListener[ index ].rowRemoved( row );
        }
    }

    private void notifyColumnAdded( Object column ) {

        DataTableModelListener[] arrayListener = getDataTableModelListeners();

        for ( int index = 0; index < arrayListener.length; index++ ) {

            arrayListener[ index ].columnAdded( column );
        }
    }

    private void notifyColumnRemoved( Object column ) {

        DataTableModelListener[] arrayListener = getDataTableModelListeners();

        for ( int index = 0; index < arrayListener.length; index++ ) {

            arrayListener[ index ].columnRemoved( column );
        }
    }
}
