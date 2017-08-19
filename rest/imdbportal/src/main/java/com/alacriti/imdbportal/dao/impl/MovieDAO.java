package com.alacriti.imdbportal.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.alacriti.imdbportal.constants.Constants;
import com.alacriti.imdbportal.constants.DBColumnConstants;
import com.alacriti.imdbportal.exceptions.DAOException;
import com.alacriti.imdbportal.models.Movie;

public class MovieDAO extends BaseDAO {
	
	public static final Logger log= Logger.getLogger(MovieDAO.class);
	
	public MovieDAO() {
		super();
	}
	public MovieDAO(Connection con) {
		super(con);
	}
	
	public List<Movie> getAllMovies() throws DAOException{
		log.debug("=========>> getAllMovies method in MovieDAO class ::");
		List<Movie> list=null;
		Statement st=null;
		ResultSet rs=null;
		String sqlCmd = null;
		try{
			list=new ArrayList<Movie>();
			st=getConnection().createStatement();
			sqlCmd = getAllMoviewSqlCmd();
			rs=st.executeQuery(sqlCmd);
			while(rs.next()){
				list.add(getMovieWithMinimumDetails(rs));
			} 
		}catch(SQLException e){
			log.error("SQL Exception Occured in selectStatement" + e.getMessage(), e);
			e.printStackTrace();
			throw new DAOException("SQL Exception Occured in selectStatement");
		}
		catch(Exception e){
			log.error("Exception Occured in getAllMovies" + e.getMessage(), e);
			e.printStackTrace();
			throw new DAOException();
		}finally{
			close(rs);
			close(st);
		}
		return list;
	}
	
	private String getAllMoviewSqlCmd(){
		return "select * from anilkumarreddyg_imdb_movie_tbl order by "
				+ DBColumnConstants.MOVIE_TBL_WEIGHTAGE +" desc";
	}
	
	public Movie getMovieWithMinimumDetails(ResultSet rs) throws DAOException{
		log.debug("=========>> getMovieWithMinimumDetails method in MovieDAO class ::");
		Movie movie=null;
		try{
			movie=new Movie(
					rs.getInt(DBColumnConstants.MOVIE_TBL_ID), 
					rs.getString(DBColumnConstants.MOVIE_TBL_TITLE),
					Constants.IMAGE_BASE_URL+
					rs.getString(DBColumnConstants.MOVIE_TBL_IMAGE_PATH),
					rs.getString(DBColumnConstants.MOVIE_TBL_SHORT_DESC),
					rs.getFloat(DBColumnConstants.MOVIE_TBL_AVG_RATING)
					);
		}catch(SQLException e){
			log.error("SQL Exception Occured in Accessing ResultSet" + e.getMessage(), e);
			e.printStackTrace();
			throw new DAOException("SQL Exception Occured in Accessing ResultSet");
		}
		catch(Exception e){
			log.error("Exception Occured in getMovieWith Minium Details" + e.getMessage(), e);
			e.printStackTrace();
			throw new DAOException();
		}
		return movie;
	}
	
	public List<Movie> getAllMoviesToAdmin(String categeory) throws DAOException{
		log.debug("=========>> getAllMoviesToAdmin method in MovieDAO class ::");
		List<Movie> list=null;
		Statement st=null;
		ResultSet rs=null;
		try{
			list=new ArrayList<Movie>();
			st=getConnection().createStatement();
			rs=st.executeQuery(getAllMoviesToAdminSqlCmd(categeory));
			while(rs.next()){
				list.add(getMovieWithMinimumDetails(rs));
			} 
		}catch(SQLException e){
			log.error("SQL Exception Occured in selectStatement" + e.getMessage(), e);
			e.printStackTrace();
			throw new DAOException("SQL Exception Occured in selectStatement");
		}
		catch(Exception e){
			log.error("Exception Occured in getAllMoviesToAdmin" + e.getMessage(), e);
			e.printStackTrace();
			throw new DAOException();
		}finally{
			close(rs);
			close(st);
		}
		return list;
	}
	
	private String getAllMoviesToAdminSqlCmd(String categeory){
		
		String sqlCmd;
		if(categeory.equals("all"))
			sqlCmd=getAllMoviewSqlCmd();
		else{
			sqlCmd="select m.* from anilkumarreddyg_imdb_movie_tbl as m, "
					+" anilkumarreddyg_imdb_movie_genres_tbl as mg,"
					+" anilkumarreddyg_imdb_genres_tbl as g "
					+" where m."+DBColumnConstants.MOVIE_TBL_ID +
					" = mg."+DBColumnConstants.MOVIE_GENRES_TBL_MID
					+" and mg."+DBColumnConstants.MOVIE_GENRES_TBL_GID
					+" =g."+DBColumnConstants.GENRES_TBL_ID
					+" and g."+DBColumnConstants.GENRES_TBL_NAME+"=\""+categeory+"\" "
					+" order by "+DBColumnConstants.MOVIE_TBL_WEIGHTAGE+" desc;";
		}
		return sqlCmd;
	}
	
	
	public int getMoviesCount() throws DAOException{
		log.debug("=========>> getMoviesCount method in MovieDAO class ::");
		int result=0;
		try{
			result=getAllMovies().size();
		}
		catch(Exception e){
			log.error("Exception Occured getMoviesCount" + e.getMessage(), e);
			e.printStackTrace();
			throw new DAOException();
		}
		return result;
	}
	
	public int getMoviesCountToAdmin(String categeory) throws DAOException{
		log.debug("=========>> getMoviesCountToAdmin method in MovieDAO class ::");
		int result=0;
		try{
			result=getAllMoviesToAdmin(categeory).size();
		}
		catch(Exception e){
			log.error("Exception Occured getMoviesCountToAdmin" + e.getMessage(), e);
			e.printStackTrace();
			throw new DAOException();
		}
		return result;
	}
	
	public Movie getMovieDeatils(int id) throws DAOException{
		log.debug("=========>> getMovieDeatils method in MovieDAO class ::");
		Movie movie=null;
		Statement st=null;
		ResultSet rs=null;
		try{
			st=getConnection().createStatement();
			rs=st.executeQuery(getMovieDeatilsSqlCmd(id));
			if(rs.next()){
				movie=new Movie(
						rs.getInt(DBColumnConstants.MOVIE_TBL_ID), 
						rs.getString(DBColumnConstants.MOVIE_TBL_TITLE),
						Constants.IMAGE_BASE_URL+
						rs.getString(DBColumnConstants.MOVIE_TBL_IMAGE_PATH),
						rs.getString(DBColumnConstants.MOVIE_TBL_SHORT_DESC),
						rs.getFloat(DBColumnConstants.MOVIE_TBL_AVG_RATING),
						rs.getString(DBColumnConstants.MOVIE_TBL_LANGUAGE),
						rs.getString(DBColumnConstants.MOVIE_TBL_DIRECTOR),
						rs.getInt(DBColumnConstants.MOVIE_TBL_YEAR),
						rs.getString(DBColumnConstants.MOVIE_TBL_DURATION),
						rs.getString(DBColumnConstants.MOVIE_TBL_DETAILED_DESC),
						rs.getString(DBColumnConstants.MOVIE_TBL_STARCAST),
						rs.getFloat(DBColumnConstants.MOVIE_TBL_WEIGHTAGE)
						);
			}
		}catch(SQLException e){
			log.error("SQL Exception Occured in selectStatement" + e.getMessage(), e);
			e.printStackTrace();
			throw new DAOException("SQL Exception Occured in selectStatement");
		}
		catch(Exception e){
			log.error("Exception Occured getMoviesDetails" + e.getMessage(), e);
			e.printStackTrace();
			throw new DAOException();
		}finally{
			close(rs);
			close(st);
		}
		return movie;
	}
	
	private String getMovieDeatilsSqlCmd(int id){
		return "select * from anilkumarreddyg_imdb_movie_tbl"
				+" where "+ DBColumnConstants.MOVIE_TBL_ID +"="+id+";";
	}
	
	public Movie getMovieDeatilsFully(int id) throws DAOException{
		log.debug("=========>> getMovieDeatilsFully method in MovieDAO class ::");
		Movie movie=null;
		try{
			movie=getMovieDeatils(id);
			movie.setAction(isMovieInGenere(id, DBColumnConstants.GENRES_TBL_NAME_ACTION));
			movie.setComedy(isMovieInGenere(id, DBColumnConstants.GENRES_TBL_NAME_COMEDY));
			movie.setRomantic(isMovieInGenere(id, DBColumnConstants.GENRES_TBL_NAME_ROMANTIC));
			movie.setScifi(isMovieInGenere(id, DBColumnConstants.GENRES_TBL_NAME_SCIFI));
		}catch(Exception e){
			log.error("Exception Occured getMoviesDetailsFully" + e.getMessage(), e);
			e.printStackTrace();
			throw new DAOException();
		}
		return movie;
	}
	
	public boolean isMovieInGenere(int mid,String categeory) throws DAOException{
		log.debug("=========>> isMovieInGenere method in MovieDAO class ::");
		boolean result=false;
		Statement st=null;
		ResultSet rs=null;
		try{
			st=getConnection().createStatement();
			rs=st.executeQuery(isMovieInGenereSqlCmd(mid,categeory));
			if(rs.next()){
				result=true;
			}
		}catch(SQLException e){
			log.error("SQL Exception Occured in selectStatement: " + e.getMessage(), e);
			e.printStackTrace();
			throw new DAOException(
					"SQL Exception Occured in selectStatementOfIsMovieInGenere method"
					);
		}
		catch(Exception e){
			log.error("Exception Occured in isMovieInGenere: "+ e.getMessage(), e);
			e.printStackTrace();
			throw new DAOException();
		}finally{
			close(rs);
			close(st);
		}
		return result;
	}
	
	private String isMovieInGenereSqlCmd(int mid,String categeory){
		return "select "+DBColumnConstants.MOVIE_GENRES_TBL_GID
				+" from anilkumarreddyg_imdb_movie_genres_tbl as mg,"
				+" anilkumarreddyg_imdb_genres_tbl as g "
				+" where mg."+DBColumnConstants.MOVIE_GENRES_TBL_MID+"="+mid
				+" and mg."+DBColumnConstants.MOVIE_GENRES_TBL_GID 
				+" = g."+DBColumnConstants.GENRES_TBL_ID
				+" and g."+DBColumnConstants.GENRES_TBL_NAME+"=\""+categeory+"\";";
	}
	
	public int getUserRate(int mid,int uid) throws DAOException{
		log.debug("=========>> getUserRate method in MovieDAO class ::");
		int result=0;
		Statement st=null;
		ResultSet rs=null;
		try{
			st=getConnection().createStatement();
			rs=st.executeQuery(getUserRateSqlCmd(mid,uid));
			if(rs.next()){
				result=rs.getInt("rating");
			}
			else{
				result=-1;
			}
		}catch(SQLException e){
			log.error("SQL Exception Occured in selectStatement: "+ e.getMessage(), e);
			e.printStackTrace();
			throw new DAOException("SQL Exception Occured in selectStatement");
		}
		catch(Exception e){
			log.error("Exception Occured getUserRate: " + e.getMessage(), e);
			e.printStackTrace();
			throw new DAOException();
		}finally{
			close(rs);
			close(st);
		}
		return result;
	}
	
	private String getUserRateSqlCmd(int mid,int uid){
		return "select "+DBColumnConstants.RATE_TBL_Rating
				+" from anilkumarreddyg_imdb_rate_tbl "
				+" where "+ DBColumnConstants.RATE_TBL_MID +" = "+mid
				+" and "+ DBColumnConstants.RATE_TBL_UID +" = "+uid+";";
		
	}
	
	public boolean isThisUserGaveRateTothisMovie(int mid,int uid) throws DAOException{
		log.debug("=========>> isThisUserGaveRateTothisMovie method in MovieDAO class ::");
		Statement st=null;
		ResultSet rs=null;
		boolean result=false;
		try{
			st=getConnection().createStatement();
			rs=st.executeQuery(isThisUserGaveRateTothisMovieSqlCmd(mid,uid));
			if(!rs.next()){
				result=true;
			}else{
				result=false;
			}
			
		}catch(SQLException e){
			log.error("SQL Exception Occured in isThisUserGaveRateTothisMovie: " + e.getMessage(), e);
			e.printStackTrace();
			throw new DAOException("SQL Exception Occured in isThisUserGaveRateTothisMovie");
		}catch(Exception e){
			log.error("Exception Occured in isThisUserGaveRateTothisMovie" + e.getMessage(), e);
			e.printStackTrace();
			throw new DAOException();
		}finally{
			close(rs);
			close(st);
		}
		return result; 
	}
	
	private String isThisUserGaveRateTothisMovieSqlCmd(int mid,int uid){
		return "select * from anilkumarreddyg_imdb_rate_tbl "
				+" where "+DBColumnConstants.RATE_TBL_MID+"="+mid
				+" and "+DBColumnConstants.RATE_TBL_UID+"="+uid;
	}
	
	public void insertUserRateToMovie(int mid,int uid,int rate) throws DAOException{
		log.debug("=========>> insertUserRateToMovie method in MovieDAO class ::");
		Statement st=null;
		try{
			st=getConnection().createStatement();
			st.execute(insertUserRateToMovieSqlCmd(mid,uid,rate));
		}catch(SQLException e){
			log.error("SQL Exception Occured in insert statement" + e.getMessage(), e);
			e.printStackTrace();
			throw new DAOException("SQL Exception Occured in insert statement");
		}catch(Exception e){
			log.error("Exception Occured in insertUserRateToMovie" + e.getMessage(), e);
			e.printStackTrace();
			throw new DAOException();
		}finally{
		
			close(st);
		}
	}
	
	private String insertUserRateToMovieSqlCmd(int mid,int uid,int rate){
		return "insert into anilkumarreddyg_imdb_rate_tbl"
				+"( "+ DBColumnConstants.RATE_TBL_MID +","
				+ DBColumnConstants.RATE_TBL_UID +","
				+ DBColumnConstants.RATE_TBL_Rating +" ) "
				+" values("+mid+","+uid+","+rate+");";
	}
	
	public void updateUserRateToMovie(int mid,int uid,int rate) throws DAOException{
		log.debug("=========>> updateUserRateToMovie method in MovieDAO class ::");
		Statement st=null;
		try{
			st=getConnection().createStatement();
			st.execute(updateUserRateToMovieSqlCmd(mid,uid,rate));
		}catch(SQLException e){
			log.error("SQL Exception Occured in update statement" + e.getMessage(), e);
			e.printStackTrace();
			throw new DAOException("SQL Exception Occured in update statement updateUserRateToMovie");
		}catch(Exception e){
			log.error("Exception Occured in updateUserRateToMovie" + e.getMessage(), e);
			e.printStackTrace();
			throw new DAOException();
		}finally{
		
			close(st);
		}
	}
	
	
	private String updateUserRateToMovieSqlCmd(int mid,int uid,int rate){
		return "update anilkumarreddyg_imdb_rate_tbl "
				+" set "+ DBColumnConstants.RATE_TBL_Rating +"="+rate
				+" where "+ DBColumnConstants.RATE_TBL_MID +"="+mid
				+" and "+ DBColumnConstants.RATE_TBL_UID +"="+uid;
	}
	
	public void setAvgRatingToMovie(int mid) throws DAOException{
		log.debug("=========>> setAvgRatingToMovie method in MovieDAO class ::");
		Statement st=null;
		try{
			st=getConnection().createStatement();
			st.execute(setAvgRatingToMovieSqlCmd(mid));
		}catch(SQLException e){
			log.error("SQL Exception Occured in update statement" + e.getMessage(), e);
			e.printStackTrace();
			throw new DAOException("SQL Exception Occured in update statement");
		}catch(Exception e){
			log.error("Exception Occured in setAvgRatingToMovie" + e.getMessage(), e);
			e.printStackTrace();
			throw new DAOException();
		}finally{
			close(st);
		}
	}
	

	private String setAvgRatingToMovieSqlCmd(int mid){
		return "update anilkumarreddyg_imdb_movie_tbl "
				+" set "+ DBColumnConstants.MOVIE_TBL_AVG_RATING +" = "
				+"(select avg("+ DBColumnConstants.RATE_TBL_Rating +") "
				+" from anilkumarreddyg_imdb_rate_tbl "
				+" group by "+ DBColumnConstants.RATE_TBL_MID 
				+" having "+ DBColumnConstants.RATE_TBL_MID +"="+mid+") "
				+" where "+ DBColumnConstants.MOVIE_TBL_ID +"="+mid+";";
	}
	
	public void setWeightedRating(float weightedRating,int mid) throws DAOException{
		log.debug("=========>> setWeightedRating method in MovieDAO class ::");
		ResultSet rs=null;
		Statement st=null;
		try{
			st=getConnection().createStatement();
			st.execute(updateWeightageSqlCmd(weightedRating,mid));
		}catch(SQLException e){
			log.error("SQL Exception Occured in setWeightedRating" + e.getMessage(), e);
			e.printStackTrace();
			throw new DAOException("SQL Exception Occured upade statement");
		}catch(Exception e){
			log.error("Exception Occured in setWeightedRating" + e.getMessage(), e);
			e.printStackTrace();
			throw new DAOException();
		}finally{
			close(rs);
			close(st);
		}
	}
	
	private String updateWeightageSqlCmd(float weightedRating,int mid){
		return "update anilkumarreddyg_imdb_movie_tbl "
				+"set "+ DBColumnConstants.MOVIE_TBL_WEIGHTAGE +"="+weightedRating
				+" where "+ DBColumnConstants.MOVIE_TBL_ID +"="+mid;
	}
	
	public float getAvgRatingOfAllMovies() throws DAOException{
		log.debug("=========>> getAvgRatingOfAllMovies method in MovieDAO class ::");
		ResultSet rs=null;
		float avgRatingOfAllMovies=0;
		Statement st=null;
		try{
			st=getConnection().createStatement();
			rs=st.executeQuery(avgRatingOfAllMoviesSqlCmd());
			if(rs.next()){
				avgRatingOfAllMovies=rs.getFloat("wholeAvg");
				rs.close();
			}
		}catch(SQLException e){
			log.error("SQL Exception Occured in getAvgRatingOfAllMovies" + e.getMessage(), e);
			e.printStackTrace();
			throw new DAOException("SQL Exception Occured in selectStatement");
		}catch(Exception e){
			log.error("Exception Occured in getAvgRatingOfAllMovies" + e.getMessage(), e);
			e.printStackTrace();
			throw new DAOException();
		}finally{
			close(rs);
			close(st);
		}
		return avgRatingOfAllMovies;
	}
	
	
	private String avgRatingOfAllMoviesSqlCmd(){
		return "select avg("+ DBColumnConstants.MOVIE_TBL_AVG_RATING +") as wholeAvg "
				+" from anilkumarreddyg_imdb_movie_tbl";
	}
	
	public float getMovieAvgRating(int mid) throws DAOException{
		log.debug("=========>> getMovieAvgRating method in MovieDAO class ::");
		ResultSet rs=null;
		float movieAvgRating=0;
		Statement st=null;
		try{
			st=getConnection().createStatement();
			rs=st.executeQuery(movieAvgRatingSqlCmd(mid));
			if(rs.next()){
				movieAvgRating=rs.getFloat(DBColumnConstants.MOVIE_TBL_AVG_RATING);
				rs.close();
			}
		}catch(SQLException e){
			log.error("SQL Exception Occured in getMovieAvgRating : " + e.getMessage(), e);
			e.printStackTrace();
			throw new DAOException("SQL Exception Occured in getMovieAvgRating");
		}catch(Exception e){
			log.error("Exception Occured in getMovieAvgRating : " + e.getMessage(), e);
			e.printStackTrace();
			throw new DAOException();
		}finally{
			close(rs);
			close(st);
		}
		return movieAvgRating;
	}

	private String movieAvgRatingSqlCmd(int mid){
		return "select "+ DBColumnConstants.MOVIE_TBL_AVG_RATING 
				+ " from anilkumarreddyg_imdb_movie_tbl "
				+" where "+ DBColumnConstants.MOVIE_TBL_ID +"="+mid;
	}
	
	public int getVotesForThisMovie(int mid) throws DAOException{
		log.debug("=========>> getVotesForThisMovie method in MovieDAO class ::");
		ResultSet rs=null;
		int votesForThisMovie=0;
		Statement st=null;
		try{
			st=getConnection().createStatement();
			rs=st.executeQuery(noOFVotesForMovie(mid));
			if(rs.next()){
				votesForThisMovie=rs.getInt("votes");
				rs.close();
			}
		}catch(SQLException e){
			log.error("SQL Exception Occured in getVotesForThisMovie : " + e.getMessage(), e);
			e.printStackTrace();
			throw new DAOException("SQL Exception Occured in select Statement");
		}catch(Exception e){
			log.error("Exception Occured in getVotesForThisMovie : " + e.getMessage(), e);
			e.printStackTrace();
			throw new DAOException();
		}finally{
			close(rs);
			close(st);
		}
		return votesForThisMovie;
	}
	
	private String noOFVotesForMovie(int mid){
		return "select count(*) as votes from anilkumarreddyg_imdb_rate_tbl where mid="+mid;
	}
	
	public void addMovieToDB(Movie movie) throws DAOException{
		log.debug("=========>> addMovieToDB method in MovieDAO class ::");
		PreparedStatement pst=null;
		Statement st=null;
		ResultSet rs=null;
		int i=0;
		int mid;
		try{
			pst=getPreparedStatement(getConnection(), addMovieToDBSqlCmd());
			pst.setString(++i,movie.getTitle());
			pst.setString(++i,movie.getImagePath());
			pst.setString(++i,movie.getShortDescription());
			pst.setString(++i,movie.getLanguage());
			pst.setString(++i,movie.getDirector());
			pst.setInt(++i, movie.getYear());
			pst.setString(++i,movie.getDuration());
			pst.setString(++i,movie.getDetailDescription());
			pst.setString(++i,movie.getStarCast());
			pst.executeUpdate();
			st=getConnection().createStatement();
			rs=st.executeQuery(getNewlyInsertedMovieId(movie.getTitle()));
			if(rs.next()){
				mid=rs.getInt(DBColumnConstants.MOVIE_TBL_ID);
				setGeneresTOMovie(
						mid,
						movie.isComedy(),
						movie.isRomantic(),
						movie.isScifi(),
						movie.isAction());
			}	
		}catch(SQLException e){
			log.error("SQL Exception Occured in addMovieToDB : " + e.getMessage(), e);
			e.printStackTrace();
			throw new DAOException("SQL Exception Occured in insertPreparedStatement");
		}catch(Exception e){
			log.error("Exception Occured in addMovieToDB : " + e.getMessage(), e);
			e.printStackTrace();
			throw new DAOException();
		}finally{
			if(pst!=null){
				try{
					pst.close();
				}catch(Exception e){
					log.error("SQL Exception Occured in closingpreparedStatement : " + e.getMessage(), e);
					throw new DAOException(
							"SQL Exception Occured in closingPreparedStatement"
							);
				}
			}
		}
	}
	
	private String addMovieToDBSqlCmd(){
		return "insert into anilkumarreddyg_imdb_movie_tbl("
				+DBColumnConstants.MOVIE_TBL_TITLE+","
				+DBColumnConstants.MOVIE_TBL_IMAGE_PATH+","
				+DBColumnConstants.MOVIE_TBL_SHORT_DESC+","
				+DBColumnConstants.MOVIE_TBL_LANGUAGE+","
				+DBColumnConstants.MOVIE_TBL_DIRECTOR+","
				+DBColumnConstants.MOVIE_TBL_YEAR+","
				+DBColumnConstants.MOVIE_TBL_DURATION+","
				+DBColumnConstants.MOVIE_TBL_DETAILED_DESC+","
				+DBColumnConstants.MOVIE_TBL_STARCAST
				+")values(?,?,?,?,?,?,?,?,?);";
	}
	private String getNewlyInsertedMovieId(String title){
		return "select "+DBColumnConstants.MOVIE_TBL_ID
				+" from anilkumarreddyg_imdb_movie_tbl " 
				+" where "+DBColumnConstants.MOVIE_TBL_TITLE+"=\""+title+"\";";
	}
	
	public void updateMovieInDB(int movieId,Movie movie) throws DAOException{
		log.debug("=========>> updateMovieInDB method in MovieDAO class ::");
		PreparedStatement pst=null;
		Statement st=null;
		int i=0;
		try{
			pst=getPreparedStatement(getConnection(), updateMovieInDBSqlCmd());
			pst.setString(++i,movie.getTitle());
			pst.setString(++i,movie.getShortDescription());
			pst.setFloat(++i,movie.getAvgRating());
			pst.setString(++i,movie.getLanguage());
			pst.setString(++i,movie.getDirector());
			pst.setInt(++i, movie.getYear());
			pst.setString(++i,movie.getDuration());
			pst.setString(++i,movie.getDetailDescription());
			pst.setString(++i,movie.getStarCast());
			pst.setFloat(++i,movie.getWeightage());
			pst.setInt(++i,movieId);
			pst.executeUpdate();
			st=getConnection().createStatement();
			st.executeUpdate(deleteGeneresOfMovie(movieId));
			setGeneresTOMovie(
					movieId,movie.isComedy(),
					movie.isRomantic(),
					movie.isScifi(),
					movie.isAction()
					);	
		}catch(SQLException e){
			log.error("SQL Exception Occured in updateMovieInDB : " + e.getMessage(), e);
			e.printStackTrace();
			throw new DAOException("SQL Exception Occured in updatePreparedStatement");
		}catch(Exception e){
			log.error("Exception Occured in updateMovieInDB : " + e.getMessage(), e);
			e.printStackTrace();
			throw new DAOException();
		}finally{
			if(pst!=null){
				try{
					pst.close();
				}catch(Exception e){
					log.error("SQL Exception Occured in closing PreparedStatement : " + e.getMessage(), e);
					throw new DAOException("SQL Exception Occured in closingPreparedStatement");
				}
			}
		}
	}
	
	private String updateMovieInDBSqlCmd(){
		return "update anilkumarreddyg_imdb_movie_tbl set "
				+ DBColumnConstants.MOVIE_TBL_TITLE +"=?, "
				+ DBColumnConstants.MOVIE_TBL_SHORT_DESC+"=?, "
				+ DBColumnConstants.MOVIE_TBL_AVG_RATING +"=?, "
				+ DBColumnConstants.MOVIE_TBL_LANGUAGE+"=?, "
				+ DBColumnConstants.MOVIE_TBL_DIRECTOR +"=?, "
				+ DBColumnConstants.MOVIE_TBL_YEAR +"=?, "
				+ DBColumnConstants.MOVIE_TBL_DURATION+"=?, "
				+ DBColumnConstants.MOVIE_TBL_DETAILED_DESC +"=?, "
				+ DBColumnConstants.MOVIE_TBL_STARCAST +"=?, "
				+ DBColumnConstants.MOVIE_TBL_WEIGHTAGE +"=? "
				+" where "+DBColumnConstants.MOVIE_TBL_ID+"=? ;";
	}
	private String deleteGeneresOfMovie(int movieId){
		return "delete from anilkumarreddyg_imdb_movie_genres_tbl "
				+" where "+ DBColumnConstants.MOVIE_GENRES_TBL_MID +" = "+movieId+";";
	}
	
	public void setGeneresTOMovie(
			int mid,
			boolean comedy,
			boolean romantic,
			boolean scifi,
			boolean action ) throws DAOException{
		log.debug("=========>> setGeneresTOMovie method in MovieDAO class ::");
		try{
			setGenere(mid,DBColumnConstants.GENRES_TBL_NAME_COMEDY,comedy);
			setGenere(mid,DBColumnConstants.GENRES_TBL_NAME_ROMANTIC,romantic);
			setGenere(mid,DBColumnConstants.GENRES_TBL_NAME_SCIFI,scifi);
			setGenere(mid,DBColumnConstants.GENRES_TBL_NAME_ACTION,action);
		}catch(DAOException e){
			log.error("DAO Exception Occured in setGeneresTOMovie: " + e.getMessage(), e);
			throw e;
		}
		catch(Exception e){
			log.error("Exception Occured in setGeneresTOMovie: " + e.getMessage(), e);
			e.printStackTrace();
			throw new DAOException("DAO Exception in setting Genere");
		}
	
	}
	
	
	public void setGenere(int mid,String categeory,boolean isAdd) throws DAOException{
		log.debug("=========>> setGenere method in MovieDAO class ::");
		Statement st=null;
		ResultSet rs=null;
		int gid=0;
		try{
			st=getConnection().createStatement();
			rs=st.executeQuery(getGenreIdSqlCmd(categeory));
			if(rs.next()){
				gid=rs.getInt(DBColumnConstants.GENRES_TBL_ID);
				rs.close();
			}
			if(isAdd){
				rs=st.executeQuery(selectGenreSqlCmd(mid,gid));
				if(!rs.next()){
					st.execute(insertGenreSqlCmd(mid,gid));
				}
			}else{
				st.execute(deleteGenreSqlCmd(mid,gid));
			}
		}catch(SQLException e){
			log.error("SQL Exception Occured in setGenere : " + e.getMessage(), e);
			e.printStackTrace();
			throw new DAOException("SQL Exception Occured in setGenere Method");
		}
		catch(Exception e){
			log.error("Exception Occured in setGenere: " + e.getMessage(), e);
			e.printStackTrace();
			throw new DAOException();
		}finally{
			close(rs);
			close(st);
		}
	}
	
	private String getGenreIdSqlCmd(String categeory){
		return "select "+DBColumnConstants.GENRES_TBL_ID
				+" from anilkumarreddyg_imdb_genres_tbl "
				+" where "+ DBColumnConstants.GENRES_TBL_NAME +"=\""+categeory+"\";";
	}
	
	private String selectGenreSqlCmd(int mid,int gid){
		return "select * from anilkumarreddyg_imdb_movie_genres_tbl "
				+" where "+ DBColumnConstants.MOVIE_GENRES_TBL_MID +"="+mid
				+" and "+ DBColumnConstants.MOVIE_GENRES_TBL_GID +"="+gid+";";
	}
	
	private String insertGenreSqlCmd(int mid,int gid){
		return "insert into anilkumarreddyg_imdb_movie_genres_tbl"
				+"( "+ DBColumnConstants.MOVIE_GENRES_TBL_MID +","
				+ DBColumnConstants.MOVIE_GENRES_TBL_GID +" )"
				+"values"+"("+mid+","+gid+");";
	}
	
	private String deleteGenreSqlCmd(int mid,int gid){
		return "delete from anilkumarreddyg_imdb_movie_genres_tbl "
				+" where "+ DBColumnConstants.MOVIE_GENRES_TBL_MID +"="+mid
				+" and "+ DBColumnConstants.MOVIE_GENRES_TBL_GID +"="+gid+";";
	}
	
	public void deleteMovieFromMovieTable(int mid) throws DAOException{
		log.debug("=========>> deleteMovieFromMovieTable method in MovieDAO class ::");
		Statement st=null;
		try{
			st=getConnection().createStatement();
			st.execute(deleteMovieFromMovieTableSqlCmd(mid));
		}catch(SQLException e){
			log.error("SQL Exception Occured in deleteMovieFromMovieTable: " + e.getMessage(), e);
			e.printStackTrace();
			throw new DAOException("SQL Exception Occured indeleteStatement");
		}catch(Exception e){
			log.error("Exception Occured in deleteMovieFromMovieTable: " + e.getMessage(), e);
			e.printStackTrace();
			throw new DAOException();
		}finally{
			close(st);
		}
	}
	
	private String deleteMovieFromMovieTableSqlCmd(int mid){
		return "delete from anilkumarreddyg_imdb_movie_tbl "
				+" where "+ DBColumnConstants.MOVIE_TBL_ID +"="+mid+";"; 
	}

	public void deleteMovieFromMovieGenreTable(int mid) throws DAOException{
		log.debug("=========>> deleteMovieFromMovieGenreTable method in MovieDAO class ::");
		Statement st=null;
		try{
			st=getConnection().createStatement();
			st.execute(deleteMovieFromMovieGenreTableSqlCmd(mid));
		}catch(SQLException e){
			log.error("SQL Exception Occured in deleteMovieFromMovieGenreTable: " + e.getMessage(), e);
			e.printStackTrace();
			throw new DAOException("SQL Exception Occured indeleteStatement");
		}catch(Exception e){
			log.error("Exception Occured in deleteMovieFromMovieGenreTable: " + e.getMessage(), e);
			e.printStackTrace();
			throw new DAOException();
		}finally{
			close(st);
		}
	}
	
	private String deleteMovieFromMovieGenreTableSqlCmd(int mid){
		return "delete from anilkumarreddyg_imdb_movie_genres_tbl "
				+" where "+ DBColumnConstants.MOVIE_GENRES_TBL_MID +"="+mid+";"; 
	}
	
	public void deleteMovieFromRateTable(int mid) throws DAOException{
		log.debug("=========>> deleteMovieFromRateTable method in MovieDAO class ::");
		Statement st=null;
		try{
			st=getConnection().createStatement();
			st.execute(deleteMovieFromRateTableSqlCmd(mid));
					
		}catch(SQLException e){
			log.error("SQL Exception Occured in deleteMovieFromRateTable: " + e.getMessage(), e);
			e.printStackTrace();
			throw new DAOException("SQL Exception Occured indeleteStatement");
		}catch(Exception e){
			log.error("Exception Occured in deleteMovieFromRateTable: " + e.getMessage(), e);
			e.printStackTrace();
			throw new DAOException();
		}finally{
			close(st);
		}
	}
	
	private String deleteMovieFromRateTableSqlCmd(int mid){
		return "delete from anilkumarreddyg_imdb_rate_tbl "
				+" where "+ DBColumnConstants.RATE_TBL_MID +"="+mid+";"; 
	}
	
}
