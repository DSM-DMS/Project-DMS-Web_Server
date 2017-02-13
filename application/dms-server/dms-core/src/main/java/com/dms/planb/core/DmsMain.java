package com.dms.planb.core;

import org.boxfox.dms.utilities.dataio.post.PostChangeDetector;

/**
 * @author KimSeongrae : Boxfoxs, JoMingyu : PlanB (city7310@naver.com)
 * 
 * -- Daedeok Software Meister High School --
 * Dormitory Management System(DMS) Project
 * 
 * Android Application HTTP Server based Vert.x Framework.
 * 
 * -- Developers' Blog --
 * @see http://boxfoxs.tistory.com/
 * @see http://city7310.blog.me/
 * 
 * -- Work --
 * Work Started at 2017.01.10
 * 
 * @see https://github.com/rlatjdfo112/DSM-Dormitory-System.git
 * @see https://app.asana.com/0/238698310665123/board
 * 
 * -- Libraries -- 
 * @see pom.xml
 */

import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;

class DmsMain {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		PostChangeDetector.getInstance().start();
		//System.setErr(new LogErrorOutputStream(System.err));
		Vertx vertx = Vertx.vertx();
		VertxOptions options = new VertxOptions();
		options.setMaxEventLoopExecuteTime(2100000000);
		vertx.deployVerticle(new DmsVerticle());
		// Branch off DmsVerticle class
	}
}