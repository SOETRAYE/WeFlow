package com.cmmobi.railwifi.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import com.cmmobi.railwifi.dao.PlayHistory;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table PLAY_HISTORY.
*/
public class PlayHistoryDao extends AbstractDao<PlayHistory, String> {

    public static final String TABLENAME = "PLAY_HISTORY";

    /**
     * Properties of entity PlayHistory.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Media_id = new Property(0, String.class, "media_id", true, "MEDIA_ID");
        public final static Property Media_type = new Property(1, int.class, "media_type", false, "MEDIA_TYPE");
        public final static Property Name = new Property(2, String.class, "name", false, "NAME");
        public final static Property Img_path = new Property(3, String.class, "img_path", false, "IMG_PATH");
        public final static Property Src_url = new Property(4, String.class, "src_url", false, "SRC_URL");
        public final static Property Location = new Property(5, String.class, "location", false, "LOCATION");
        public final static Property Percent = new Property(6, String.class, "percent", false, "PERCENT");
        public final static Property Totaltime = new Property(7, String.class, "totaltime", false, "TOTALTIME");
    };


    public PlayHistoryDao(DaoConfig config) {
        super(config);
    }
    
    public PlayHistoryDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'PLAY_HISTORY' (" + //
                "'MEDIA_ID' TEXT PRIMARY KEY NOT NULL ," + // 0: media_id
                "'MEDIA_TYPE' INTEGER NOT NULL ," + // 1: media_type
                "'NAME' TEXT," + // 2: name
                "'IMG_PATH' TEXT," + // 3: img_path
                "'SRC_URL' TEXT," + // 4: src_url
                "'LOCATION' TEXT," + // 5: location
                "'PERCENT' TEXT," + // 6: percent
                "'TOTALTIME' TEXT);"); // 7: totaltime
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'PLAY_HISTORY'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, PlayHistory entity) {
        stmt.clearBindings();
 
        String media_id = entity.getMedia_id();
        if (media_id != null) {
            stmt.bindString(1, media_id);
        }
        stmt.bindLong(2, entity.getMedia_type());
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(3, name);
        }
 
        String img_path = entity.getImg_path();
        if (img_path != null) {
            stmt.bindString(4, img_path);
        }
 
        String src_url = entity.getSrc_url();
        if (src_url != null) {
            stmt.bindString(5, src_url);
        }
 
        String location = entity.getLocation();
        if (location != null) {
            stmt.bindString(6, location);
        }
 
        String percent = entity.getPercent();
        if (percent != null) {
            stmt.bindString(7, percent);
        }
 
        String totaltime = entity.getTotaltime();
        if (totaltime != null) {
            stmt.bindString(8, totaltime);
        }
    }

    /** @inheritdoc */
    @Override
    public String readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public PlayHistory readEntity(Cursor cursor, int offset) {
        PlayHistory entity = new PlayHistory( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // media_id
            cursor.getInt(offset + 1), // media_type
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // name
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // img_path
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // src_url
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // location
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // percent
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7) // totaltime
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, PlayHistory entity, int offset) {
        entity.setMedia_id(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setMedia_type(cursor.getInt(offset + 1));
        entity.setName(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setImg_path(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setSrc_url(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setLocation(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setPercent(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setTotaltime(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
     }
    
    /** @inheritdoc */
    @Override
    protected String updateKeyAfterInsert(PlayHistory entity, long rowId) {
        return entity.getMedia_id();
    }
    
    /** @inheritdoc */
    @Override
    public String getKey(PlayHistory entity) {
        if(entity != null) {
            return entity.getMedia_id();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}
