package com.jspxcms.core.domaindsl;

import com.jspxcms.core.domain.InfoFav;
import com.mysema.query.types.Path;
import com.mysema.query.types.PathMetadata;
import com.mysema.query.types.path.*;

import javax.annotation.Generated;
import java.util.Date;

import static com.mysema.query.types.PathMetadataFactory.forVariable;

/**
 * Created by lidengqi on 2016/12/27.
 *
 * QInfoFav is a Querydsl query type for InfoFav
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QInfoFav extends EntityPathBase<InfoFav> {
    private static final long serialVersionUID = 1480212597;

    private static final PathInits INITS = PathInits.DIRECT;

    public static final QInfoFav infoFav = new QInfoFav("infoFav");

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

    public QInfoFav(String variable) {
        this(InfoFav.class, forVariable(variable), INITS);
    }

    @SuppressWarnings("all")
    public QInfoFav(Path<? extends InfoFav> path) {
        this((Class)path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QInfoFav(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QInfoFav(PathMetadata<?> metadata, PathInits inits) {
        this(InfoFav.class, metadata, inits);
    }

    public QInfoFav(Class<? extends InfoFav> type, PathMetadata<?> metadata, PathInits inits) {
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
