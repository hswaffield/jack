
/**
 * Autogenerated by Jack
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 */
/* generated from migration version 20110324000133 */
package com.rapleaf.jack.test_project.database_1.impl;

import java.util.Arrays;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.sql.Timestamp;

import com.rapleaf.jack.AbstractDatabaseModel;
import com.rapleaf.jack.BaseDatabaseConnection;

import com.rapleaf.jack.test_project.database_1.models.Comment;
import com.rapleaf.jack.test_project.database_1.iface.ICommentPersistence;

import com.rapleaf.jack.test_project.IDatabases;

public class BaseCommentPersistenceImpl extends AbstractDatabaseModel<Comment> implements ICommentPersistence {
  private final IDatabases databases;

  public BaseCommentPersistenceImpl(BaseDatabaseConnection conn, IDatabases databases) {
    super(conn, "comments", Arrays.asList("content", "commenter_id", "commented_on_id"));
    this.databases = databases;
  }


  public Comment create(final String content, final Integer commenter_id, final Integer commented_on_id) throws IOException {
    int __id = realCreate(new AttrSetter() {
      public void set(PreparedStatement stmt) throws SQLException {
        if (content == null) {
          stmt.setNull(1, java.sql.Types.CHAR);
        } else {
          stmt.setString(1, content);
        }
        if (commenter_id == null) {
          stmt.setNull(2, java.sql.Types.INTEGER);
        } else {
          stmt.setInt(2, commenter_id);
        }
        if (commented_on_id == null) {
          stmt.setNull(3, java.sql.Types.INTEGER);
        } else {
          stmt.setInt(3, commented_on_id);
        }
      }
    }, getInsertStatement(Arrays.asList("content", "commenter_id", "commented_on_id")));
    Comment newInst = new Comment(__id, content, commenter_id, commented_on_id, databases);
    cachedById.put(__id, newInst);
    clearForeignKeyCache();
    return newInst;
  }


  @Override
  protected void setAttrs(Comment model, PreparedStatement stmt) throws SQLException {
    if (model.getContent() == null) {
      stmt.setNull(1, java.sql.Types.CHAR);
    } else {
      stmt.setString(1, model.getContent());
    }
    if (model.getCommenterId() == null) {
      stmt.setNull(2, java.sql.Types.INTEGER);
    } else {
      stmt.setInt(2, model.getCommenterId());
    }
    if (model.getCommentedOnId() == null) {
      stmt.setNull(3, java.sql.Types.INTEGER);
    } else {
      stmt.setInt(3, model.getCommentedOnId());
    }
    stmt.setLong(4, model.getId());
  }

  @Override
  protected Comment instanceFromResultSet(ResultSet rs) throws SQLException {
    return new Comment(rs.getInt("id"),
      rs.getString("content"),
      getIntOrNull(rs, "commenter_id"),
      getIntOrNull(rs, "commented_on_id"),
      databases
    );
  }
}
