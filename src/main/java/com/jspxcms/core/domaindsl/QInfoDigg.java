package com.jspxcms.core.domaindsl;

import com.jspxcms.core.domain.InfoDigg;
import com.mysema.query.types.Path;
import com.mysema.query.types.PathMetadata;
import com.mysema.query.types.path.*;

import javax.annotation.Generated;
import java.util.Date;

import static com.mysema.query.types.PathMetadataFactory.forVariable;

/**
 * Created by lidengqi on 2016/12/26.
 *
 * QInfoDigg is a Querydsl query type for InfoDigg
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QInfoDigg extends EntityPathBase<InfoDigg> {
    private static final long serialVersionUID = 1480212596;

    private static final PathInits INITS = PathInits.DIRECT;

    public static final QInfoDigg infoDigg = new QInfoDigg("infoDigg");

    public final QVoteMark _super;

    //inherited
    public final NumberPath<Integer> id;

    // inherited
    public final QUser user;

    //inherited
    public final StringPath ftype;

    //inherited
    public final NumberPath<Integer> fid;

    //inherited
    public final DateTimePath<Date> date;

    public final QInfo info;

    //inherited
    public final StringPath ip;

    //inherited
    public final StringPath cookie;

    public QInfoDigg(String variable) {
        this(InfoDigg.class, forVariable(variable), INITS);
    }

    @SuppressWarnings("all")
    public QInfoDigg(Path<? extends InfoDigg> path) {
        this((Class)path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QInfoDigg(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QInfoDigg(PathMetadata<?> metadata, PathInits inits) {
        this(InfoDigg.class, metadata, inits);
    }

    public QInfoDigg(Class<? extends InfoDigg> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new QVoteMark(type, metadata, inits);
        this.user = _super.user;
        this.fid = _super.fid;
        this.ftype = _super.ftype;
        this.id = _super.id;
        this.info = inits.isInitialized("info") ? new QInfo(forProperty("info"), inits.get("info")) : null;
        this.date = _super.date;
        this.ip = _super.ip;
        this.cookie = _super.cookie;
    }
}
