package resource.bean.report;

import resource.bean.report.base.BaseBiQuartzJobLog;

public class BiQuartzJobLog extends BaseBiQuartzJobLog implements
		java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/* [CONSTRUCTOR MARKER BEGIN] */
	public BiQuartzJobLog() {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public BiQuartzJobLog(java.lang.String id) {
		super(id);
	}

	/* [CONSTRUCTOR MARKER END] */

}