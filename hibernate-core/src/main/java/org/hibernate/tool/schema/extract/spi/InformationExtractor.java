/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.tool.schema.extract.spi;

import java.util.Collection;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.tool.schema.extract.internal.TableInformationImpl;

/**
 * Contract for extracting information about objects in the database schema(s).  To an extent, the contract largely
 * mirrors parts of the JDBC {@link java.sql.DatabaseMetaData} contract.  THe intention is to insulate callers
 * from {@link java.sql.DatabaseMetaData} since on many databases there are better ways to get information from
 * the meta schema.
 *
 * NOTE : Concepts here taken largely from the {@code MetaDataDialect} class in Hibernate Tools.
 *
 * @author Steve Ebersole
 */
public interface InformationExtractor {

	boolean schemaExists(Identifier catalog, Identifier schema);

	/**
	 * Look for a matching table.
	 *
	 * @param catalog Can be {@code null}, indicating that any catalog may be considered a match.  A
	 * non-{@code null} value indicates that search should be limited to the passed catalog.
	 * @param schema Can  be {@code null}, indicating that any schema may be considered a match.  A
	 * non-{@code null} value indicates that search should be limited to the passed schema .
	 * @param tableName The name of the table to look for.
	 *
	 * @return table info for the matching table
	 */
	public TableInformation getTable(Identifier catalog, Identifier schema, Identifier tableName);

	/**
	 * Return information about column for the given table.  Typically called from the TableInformation itself
	 * as part of on-demand initialization of its state.
	 *
	 * @param tableInformation table info for the matching table
	 * @param columnIdentifier The column identifier for which to locate column
	 *
	 * @return The extracted column information
	 */
	public ColumnInformation getColumn(TableInformation tableInformation, Identifier columnIdentifier);

	/**
	 * Extract information about the given table's primary key.
	 *
	 * @param tableInformation The table for which to locate primary key information,
	 *
	 * @return The extracted primary key information
	 */
	public PrimaryKeyInformation getPrimaryKey(TableInformationImpl tableInformation);

	/**
	 * Extract information about indexes defined against the given table.  Typically called from the TableInformation
	 * itself as part of on-demand initialization of its state.
	 *
	 * @param tableInformation The table for which to locate indexes
	 *
	 * @return The extracted index information
	 */
	public Iterable<IndexInformation> getIndexes(TableInformation tableInformation);

	/**
	 * Extract information about foreign keys defined on the given table (targeting or point-at other tables).
	 * Typically called from the TableInformation itself as part of on-demand initialization of its state.
	 *
	 * @param tableInformation The table for which to locate foreign-keys
	 *
	 * @return The extracted foreign-key information
	 */
	public Iterable<ForeignKeyInformation> getForeignKeys(TableInformation tableInformation);
}
