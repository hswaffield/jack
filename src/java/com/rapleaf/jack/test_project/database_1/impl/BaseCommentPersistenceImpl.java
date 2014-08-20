
/**
 * Autogenerated by Jack
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 */
package com.rapleaf.jack.test_project.database_1.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.sql.Timestamp;

import com.rapleaf.jack.AbstractDatabaseModel;
import com.rapleaf.jack.BaseDatabaseConnection;
import com.rapleaf.jack.IQueryOperator;
import com.rapleaf.jack.WhereConstraint;
import com.rapleaf.jack.ModelQuery;
import com.rapleaf.jack.ModelWithId;
import com.rapleaf.jack.test_project.database_1.iface.ICommentPersistence;
import com.rapleaf.jack.test_project.database_1.models.Comment;
import com.rapleaf.jack.test_project.database_1.query.CommentQueryBuilder;


import com.rapleaf.jack.test_project.IDatabases;

public class BaseCommentPersistenceImpl extends AbstractDatabaseModel<Comment> implements ICommentPersistence {
  private final IDatabases databases;

  public BaseCommentPersistenceImpl(BaseDatabaseConnection conn, IDatabases databases) {
    super(conn, "comments", Arrays.asList("content", "commenter_id", "commented_on_id", "created_at"));
    this.databases = databases;
  }

  @Override
  public Comment create(Map<Enum, Object> fieldsMap) throws IOException {
    String content = (String) fieldsMap.get(Comment._Fields.content);
    int commenter_id = (Integer) fieldsMap.get(Comment._Fields.commenter_id);
    long commented_on_id = (Long) fieldsMap.get(Comment._Fields.commented_on_id);
    Long created_at_tmp = (Long) fieldsMap.get(Comment._Fields.created_at);
    long created_at = created_at_tmp == null ? 28800000 : created_at_tmp;
    return create(content, commenter_id, commented_on_id, created_at);
  }

  public Comment create(final String content, final int commenter_id, final long commented_on_id) throws IOException {
    return this.create(content, commenter_id, commented_on_id, System.currentTimeMillis());
  }
  public Comment create(final String content, final int commenter_id, final long commented_on_id, final long created_at) throws IOException {
    long __id = realCreate(new AttrSetter() {
      public void set(PreparedStatement stmt) throws SQLException {
        if (content == null) {
          stmt.setNull(1, java.sql.Types.CHAR);
        } else {
          stmt.setString(1, content);
        }
          stmt.setInt(2, commenter_id);
          stmt.setLong(3, commented_on_id);
          stmt.setTimestamp(4, new Timestamp(created_at));
      }
    }, getInsertStatement(Arrays.<String>asList("content", "commenter_id", "commented_on_id", "created_at")));
    Comment newInst = new Comment(__id, content, commenter_id, commented_on_id, created_at, databases);
    newInst.setCreated(true);
    cachedById.put(__id, newInst);
    clearForeignKeyCache();
    return newInst;
  }


  public Comment create(final int commenter_id, final long commented_on_id, final long created_at) throws IOException {
    long __id = realCreate(new AttrSetter() {
      public void set(PreparedStatement stmt) throws SQLException {
          stmt.setInt(1, commenter_id);
          stmt.setLong(2, commented_on_id);
          stmt.setTimestamp(3, new Timestamp(created_at));
      }
    }, getInsertStatement(Arrays.<String>asList("commenter_id", "commented_on_id", "created_at")));
    Comment newInst = new Comment(__id, null, commenter_id, commented_on_id, created_at, databases);
    newInst.setCreated(true);
    cachedById.put(__id, newInst);
    clearForeignKeyCache();
    return newInst;
  }


  public Comment createDefaultInstance() throws IOException {
    return create(0, 0L, 0L);
  }

  public Set<Comment> find(Map<Enum, Object> fieldsMap) throws IOException {
    return find(null, fieldsMap);
  }

  public Set<Comment> find(Set<Long> ids, Map<Enum, Object> fieldsMap) throws IOException {
    Set<Comment> foundSet = new HashSet<Comment>();
    
    if (fieldsMap == null || fieldsMap.isEmpty()) {
      return foundSet;
    }

    StringBuilder statementString = new StringBuilder();
    statementString.append("SELECT * FROM comments WHERE (");
    List<Object> nonNullValues = new ArrayList<Object>();
    List<Comment._Fields> nonNullValueFields = new ArrayList<Comment._Fields>();

    Iterator<Map.Entry<Enum, Object>> iter = fieldsMap.entrySet().iterator();
    while (iter.hasNext()) {
      Map.Entry<Enum, Object> entry = iter.next();
      Enum field = entry.getKey();
      Object value = entry.getValue();
      
      String queryValue = value != null ? " = ? " : " IS NULL";
      if (value != null) {
        nonNullValueFields.add((Comment._Fields) field);
        nonNullValues.add(value);
      }

      statementString.append(field).append(queryValue);
      if (iter.hasNext()) {
        statementString.append(" AND ");
      }
    }
    if (ids != null) statementString.append(" AND ").append(getIdSetCondition(ids));
    statementString.append(")");

    PreparedStatement preparedStatement = getPreparedStatement(statementString.toString());

    for (int i = 0; i < nonNullValues.size(); i++) {
      Comment._Fields field = nonNullValueFields.get(i);
      try {
        switch (field) {
          case content:
            preparedStatement.setString(i+1, (String) nonNullValues.get(i));
            break;
          case commenter_id:
            preparedStatement.setInt(i+1, (Integer) nonNullValues.get(i));
            break;
          case commented_on_id:
            preparedStatement.setLong(i+1, (Long) nonNullValues.get(i));
            break;
          case created_at:
            preparedStatement.setTimestamp(i+1, new Timestamp((Long) nonNullValues.get(i)));
            break;
        }
      } catch (SQLException e) {
        throw new IOException(e);
      }
    }
    executeQuery(foundSet, preparedStatement);

    return foundSet;
  }

  public Set<Comment> find(ModelQuery query) throws IOException {
    Set<Comment> foundSet = new HashSet<Comment>();
    
    if (query.getConstraints() == null || query.getConstraints().isEmpty()) {
      Set<Long> ids = query.getIdSet();
      if(ids != null && !ids.isEmpty()){
        return find(ids);
      }
      return foundSet;
    }

    String statement = query.getSelectClause();
    statement += " FROM comments ";
    statement += query.getWhereClause();
    statement += " ";
    statement += query.getLimitClause();

    PreparedStatement preparedStatement = getPreparedStatement(statement);
    PreparedStatement completeStatement = getCompleteStatement(preparedStatement, query);
    executeQuery(foundSet, completeStatement);

    return foundSet;
  }

  public List<Comment> findWithOrder(ModelQuery query) throws IOException {
    List<Comment> foundList = new ArrayList<Comment>();
    
    if (query.getConstraints() == null || query.getConstraints().isEmpty()) {
      Set<Long> ids = query.getIdSet();
      if(ids != null && !ids.isEmpty()){
        return findWithOrder(ids, query);
      }
      return foundList;
    }

    String statement = query.getSelectClause();
    statement += " FROM comments ";
    statement += query.getWhereClause();
    statement += " ";
    statement += query.getOrderByClause();
    statement += " ";
    statement += query.getLimitClause();

    PreparedStatement preparedStatement = getPreparedStatement(statement);
    PreparedStatement completeStatement = getCompleteStatement(preparedStatement, query);
    executeQuery(foundList, completeStatement);

    return foundList;
  }

  private PreparedStatement getCompleteStatement(PreparedStatement preparedStatement, ModelQuery query) throws IOException {
    int index = 0;
    for (WhereConstraint constraint : query.getConstraints()) {
      Comment._Fields field = (Comment._Fields)constraint.getField();
      for (Object parameter : constraint.getParameters()) {
        if (parameter == null) {
          continue;
        }
        try {
          switch (field) {
            case content:
              preparedStatement.setString(++index, (String) parameter);
              break;
            case commenter_id:
              preparedStatement.setInt(++index, (Integer) parameter);
              break;
            case commented_on_id:
              preparedStatement.setLong(++index, (Long) parameter);
              break;
            case created_at:
              preparedStatement.setTimestamp(++index, new Timestamp((Long) parameter));
              break;
          }
        } catch (SQLException e) {
          throw new IOException(e);
        }
      }
    }
    return preparedStatement;
  }

  @Override
  protected void setAttrs(Comment model, PreparedStatement stmt) throws SQLException {
    if (model.getContent() == null) {
      stmt.setNull(1, java.sql.Types.CHAR);
    } else {
      stmt.setString(1, model.getContent());
    }
    {
      stmt.setInt(2, model.getCommenterId());
    }
    {
      stmt.setLong(3, model.getCommentedOnId());
    }
    {
      stmt.setTimestamp(4, new Timestamp(model.getCreatedAt()));
    }
    stmt.setLong(5, model.getId());
  }

  @Override
  protected Comment instanceFromResultSet(ResultSet rs) throws SQLException {
    return new Comment(rs.getLong("id"),
      rs.getString("content"),
      getIntOrNull(rs, "commenter_id"),
      getLongOrNull(rs, "commented_on_id"),
      getDateAsLong(rs, "created_at"),
      databases
    );
  }

  public Set<Comment> findByContent(final String value) throws IOException {
    return find(new HashMap<Enum, Object>(){{put(Comment._Fields.content, value);}});
  }

  public Set<Comment> findByCommenterId(final int value) throws IOException {
    return find(new HashMap<Enum, Object>(){{put(Comment._Fields.commenter_id, value);}});
  }

  public Set<Comment> findByCommentedOnId(final long value) throws IOException {
    return find(new HashMap<Enum, Object>(){{put(Comment._Fields.commented_on_id, value);}});
  }

  public Set<Comment> findByCreatedAt(final long value) throws IOException {
    return find(new HashMap<Enum, Object>(){{put(Comment._Fields.created_at, value);}});
  }

  public CommentQueryBuilder query() {
    return new CommentQueryBuilder(this);
  }
}
