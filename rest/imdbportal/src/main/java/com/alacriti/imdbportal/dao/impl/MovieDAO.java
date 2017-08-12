package com.alacriti.imdbportal.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.alacriti.imdbportal.constants.Constants;
import com.alacriti.imdbportal.exceptions.DAOException;
import com.alacriti.imdbportal.models.Movie;

public class MovieDAO extends BaseDAO {
	
	public MovieDAO() {
		super();
	}
	public MovieDAO(Connection con) {
		super(con);
	}
	
	 
	
	public List<Movie> getAllMovies() throws DAOException{
		List<Movie> list=null;
		Statement st=null;
		ResultSet rs=null;
		try{
			list=new ArrayList<Movie>();
			st=getConnection().createStatement();
			rs=st.executeQuery("select * from anilkumarreddyg_imdb_movie_tbl order by weightage desc;");
			while(rs.next()){
				list.add(new Movie(rs.getInt("id"), rs.getString("title"),Constants.IMAGE_BASE_URL+rs.getString("image_path"),rs.getString("short_desc"), rs.getFloat("avg_rating")));
			} 
		}catch(SQLException e){
			e.printStackTrace();
			throw new DAOException("SQL Exception Occured in selectStatement");
		}
		catch(Exception e){
			e.printStackTrace();
			throw new DAOException();
		}finally{
			close(rs);
			close(st);
		}
		return list;
	}
	
	public List<Movie> getAllMoviesToAdmin(String categeory) throws DAOException{
		List<Movie> list=null;
		Statement st=null;
		ResultSet rs=null;
		String sqlCmd;
		try{
			list=new ArrayList<Movie>();
			st=getConnection().createStatement();
			if(categeory.equals("all"))
				rs=st.executeQuery("select * from anilkumarreddyg_imdb_movie_tbl order by weightage desc;");
			else{
				sqlCmd="select m.* from anilkumarreddyg_imdb_movie_tbl as m,anilkumarreddyg_imdb_movie_genres_tbl as mg,anilkumarreddyg_imdb_genres_tbl as g where m.id=mg.mid and mg.gid=g.id and g.name=\""+categeory+"\" order by weightage desc;";
				rs=st.executeQuery(sqlCmd);
			}
			while(rs.next()){
				list.add(new Movie(rs.getInt("id"), rs.getString("title"),Constants.IMAGE_BASE_URL+rs.getString("image_path"),rs.getString("short_desc"), rs.getFloat("avg_rating")));
			} 
		}catch(SQLException e){
			e.printStackTrace();
			throw new DAOException("SQL Exception Occured in selectStatement");
		}
		catch(Exception e){
			e.printStackTrace();
			throw new DAOException();
		}finally{
			close(rs);
			close(st);
		}
		return list;
	}
	
	public int getMoviesCount() throws DAOException{
		int result=0;
		try{
			result=getAllMovies().size();
		}
		catch(Exception e){
			e.printStackTrace();
			throw new DAOException();
		}
		return result;
	}
	
	public int getMoviesCountToAdmin(String categeory) throws DAOException{
		int result=0;
		try{
			result=getAllMoviesToAdmin(categeory).size();
		}
		catch(Exception e){
			e.printStackTrace();
			throw new DAOException();
		}
		return result;
	}
	
	public Movie getMovieDeatils(int id) throws DAOException{
		Movie movie=null;
		Statement st=null;
		String query;
		ResultSet rs=null;
		try{
			st=getConnection().createStatement();
			query="select * from anilkumarreddyg_imdb_movie_tbl where id="+id+";";
			rs=st.executeQuery(query);
			if(rs.next()){
				movie=new Movie(
						rs.getInt("id"),
						rs.getString("title"),
						Constants.IMAGE_BASE_URL+rs.getString("image_path"),
						rs.getString("short_desc"),
						rs.getFloat("avg_rating"),
						rs.getString("lang"),
						rs.getString("director"),
						rs.getInt("year"),rs.getString("duration"),
						rs.getString("detailed_desc"),
						rs.getString("starCast"),
						rs.getFloat("weightage")
						);
			}
		}catch(SQLException e){
			e.printStackTrace();
			throw new DAOException("SQL Exception Occured in selectStatement");
		}
		catch(Exception e){
			e.printStackTrace();
			throw new DAOException();
		}finally{
			close(rs);
			close(st);
		}
		return movie;
	}
	
	public Movie getMovieDeatilsFully(int id) throws DAOException{
		Movie movie=null;
		Statement st=null;
		ResultSet rs=null;
		try{
			movie=getMovieDeatils(id);
			movie.setAction(isMovieInGenere(id, "action"));
			movie.setComedy(isMovieInGenere(id, "comedy"));
			movie.setRomantic(isMovieInGenere(id, "romantic"));
			movie.setScifi(isMovieInGenere(id, "scifi"));
		}catch(Exception e){
			e.printStackTrace();
			throw new DAOException();
		}finally{
			close(rs);
			close(st);
		}
		return movie;
	}
	
	public boolean isMovieInGenere(int mid,String categeory) throws DAOException{
		boolean result=false;
		Statement st=null;
		String query;
		ResultSet rs=null;
		try{
			query="select gid from anilkumarreddyg_imdb_movie_genres_tbl as mg,anilkumarreddyg_imdb_genres_tbl as g where mg.mid="+mid+" and mg.gid=g.id and g.name=\""+categeory+"\";";
			st=getConnection().createStatement();
			rs=st.executeQuery(query);
			if(rs.next()){
				result=true;
			}
		}catch(SQLException e){
			e.printStackTrace();
			throw new DAOException("SQL Exception Occured in selectStatementOfIsMovieInGenere method");
		}
		catch(Exception e){
			e.printStackTrace();
			throw new DAOException();
		}finally{
			close(rs);
			close(st);
		}
		return result;
	}
	
	
	public int getUserRate(int mid,int uid) throws DAOException{
		int result=0;
		Statement st=null;
		String query;
		ResultSet rs=null;
		try{
			st=getConnection().createStatement();
			query="select rating from anilkumarreddyg_imdb_rate_tbl where mid="+mid+" and uid= "+uid+";";
			rs=st.executeQuery(query);
			if(rs.next()){
				result=rs.getInt("rating");
			}
			else{
				result=-1;
			}
		}catch(SQLException e){
			e.printStackTrace();
			throw new DAOException("SQL Exception Occured in selectStatement");
		}
		catch(Exception e){
			e.printStackTrace();
			throw new DAOException();
		}finally{
			close(rs);
			close(st);
		}
		return result;
	}
	
	public void setUserRatetoMovie(int mid,int uid,int rate) throws DAOException{
		Statement st=null;
		ResultSet rs=null;
		String query;
		try{
			st=getConnection().createStatement();
			query="select * from anilkumarreddyg_imdb_rate_tbl where mid="+mid+" and uid="+uid;
			rs=st.executeQuery(query);
			if(!rs.next()){
				query="insert into anilkumarreddyg_imdb_rate_tbl(mid,uid,rating) values("+mid+","+uid+","+rate+");";
				st.execute(query);
			}else{
				query="update anilkumarreddyg_imdb_rate_tbl set rating="+rate+" where mid="+mid+" and uid="+uid;
				st.execute(query);
			}
			query="update anilkumarreddyg_imdb_movie_tbl set avg_rating=(select avg(rating) from anilkumarreddyg_imdb_rate_tbl group by mid having mid="+mid+") where id="+mid+";";
			st.execute(query);
			setWeightedRating(mid,st);
			
		}catch(SQLException e){
			e.printStackTrace();
			throw new DAOException("SQL Exception Occured in Statement of set userRate");
		}catch(Exception e){
			e.printStackTrace();
			throw new DAOException();
		}finally{
			close(rs);
			close(st);
		}
	}
	
	public void setWeightedRating(int mid,Statement st) throws DAOException{
		ResultSet rs=null;
		String query;
		float weightedRating;
		float avgRatingOfAllMovies=0;
		float movieAvgRating=0;
		int votesForThisMovie=0;
		try{
			query="select avg(weightage) as wholeAvg from anilkumarreddyg_imdb_movie_tbl";
			rs=st.executeQuery(query);
			if(rs.next()){
				avgRatingOfAllMovies=rs.getFloat("wholeAvg");
				rs.close();
			}
			query="select weightage from anilkumarreddyg_imdb_movie_tbl where id="+mid;
			rs=st.executeQuery(query);
			if(rs.next()){
				movieAvgRating=rs.getFloat("weightage");
				rs.close();
			}
			query="select count(*) as votes from anilkumarreddyg_imdb_rate_tbl where mid="+mid;
			rs=st.executeQuery(query);
			if(rs.next()){
				votesForThisMovie=rs.getInt("votes");
				rs.close();
			}
		
			weightedRating=(movieAvgRating+avgRatingOfAllMovies)/((float)votesForThisMovie+Constants.MIN_VOTES_FOR_TOP);
			query="update anilkumarreddyg_imdb_movie_tbl set weightage="+weightedRating+" where id="+mid;
			st.execute(query);
		}catch(SQLException e){
			e.printStackTrace();
			throw new DAOException("SQL Exception Occured in Statement of set userRate");
		}catch(Exception e){
			e.printStackTrace();
			throw new DAOException();
		}
		
	}
	
	
	public void addMovieToDB(Movie movie) throws DAOException{
		PreparedStatement pst=null;
		Statement st=null;
		ResultSet rs=null;
		String sqlCmd;
		int i=0;
		int mid;
		try{
			sqlCmd="insert into anilkumarreddyg_imdb_movie_tbl(title,image_path,short_desc,lang,director,year,duration,detailed_desc,starcast)values(?,?,?,?,?,?,?,?,?);";
			pst=getPreparedStatement(getConnection(), sqlCmd);
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
			sqlCmd="select id from anilkumarreddyg_imdb_movie_tbl where title=\""+movie.getTitle()+"\";";
			rs=st.executeQuery(sqlCmd);
			if(rs.next()){
				mid=rs.getInt("id");
				setGeneresTOMovie(mid,movie.isComedy(),movie.isRomantic(),movie.isScifi(),movie.isAction());
			}	
		}catch(SQLException e){
			e.printStackTrace();
			throw new DAOException("SQL Exception Occured in insertPreparedStatement");
		}catch(Exception e){
			e.printStackTrace();
			throw new DAOException();
		}finally{
			if(pst!=null){
				try{
					pst.close();
				}catch(Exception e){
					throw new DAOException("SQL Exception Occured in closingPreparedStatement");
				}
			}
		}
	}
	
	public void updateMovieInDB(int movieId,Movie movie) throws DAOException{
		PreparedStatement pst=null;
		Statement st=null;
		String sqlCmd;
		int i=0;
		try{
			sqlCmd="update anilkumarreddyg_imdb_movie_tbl set title=?,short_desc=?,avg_rating=?,lang=?,director=?,year=?,duration=?,detailed_desc=?,starcast=?,weightage=? where id=?";
			pst=getPreparedStatement(getConnection(), sqlCmd);
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
			sqlCmd="delete from anilkumarreddyg_imdb_movie_genres_tbl where mid="+movieId+";";
			st.executeUpdate(sqlCmd);
			setGeneresTOMovie(movieId,movie.isComedy(),movie.isRomantic(),movie.isScifi(),movie.isAction());	
		}catch(SQLException e){
			e.printStackTrace();
			throw new DAOException("SQL Exception Occured in insertPreparedStatement");
		}catch(Exception e){
			e.printStackTrace();
			throw new DAOException();
		}finally{
			if(pst!=null){
				try{
					pst.close();
				}catch(Exception e){
					throw new DAOException("SQL Exception Occured in closingPreparedStatement");
				}
			}
		}
	}
	
	
	
	
	public void setGeneresTOMovie(int mid,boolean comedy,boolean romantic,boolean scifi,boolean action) throws DAOException{
		
		try{
			setGenere(mid,"comedy",comedy);
			setGenere(mid,"romantic",romantic);
			setGenere(mid,"scifi",scifi);
			setGenere(mid,"action",action);
		}catch(DAOException e){
			throw e;
		}
		catch(Exception e){
			e.printStackTrace();
			throw new DAOException("DAO Exception in setting Genere");
		}
	
	}
	
	
	public void setGenere(int mid,String categeory,boolean isAdd) throws DAOException{
		Statement st=null;
		ResultSet rs=null;
		int gid=0;
		String sqlCmd;
		try{
			st=getConnection().createStatement();
			sqlCmd="select id from anilkumarreddyg_imdb_genres_tbl where name=\""+categeory+"\";";
			rs=st.executeQuery(sqlCmd);
			if(rs.next()){
				gid=rs.getInt("id");
				rs.close();
			}
			if(isAdd){
				sqlCmd="select * from anilkumarreddyg_imdb_movie_genres_tbl where mid="+mid+" and gid="+gid+";";
				rs=st.executeQuery(sqlCmd);
				if(!rs.next()){
					sqlCmd="insert into anilkumarreddyg_imdb_movie_genres_tbl(mid,gid)values("+mid+","+gid+");";
					st.execute(sqlCmd);
				}
			}else{
				sqlCmd="delete from anilkumarreddyg_imdb_movie_genres_tbl where mid="+mid+" and gid="+gid+";";
				st.execute(sqlCmd);
			}
		}catch(SQLException e){
			e.printStackTrace();
			throw new DAOException("SQL Exception Occured in setGenere Method");
		}
		catch(Exception e){
			e.printStackTrace();
			throw new DAOException();
		}finally{
			close(rs);
			close(st);
		}
	}
	
	
	public void deleteMovieFromDB(int mid) throws DAOException{
		Statement st=null;
		String query;
		try{
			st=getConnection().createStatement();
			query="delete from anilkumarreddyg_imdb_movie_genres_tbl where mid="+mid+";";
			st.execute(query);
			query="delete from anilkumarreddyg_imdb_rate_tbl where mid="+mid+";";
			st.execute(query);
			query="delete from anilkumarreddyg_imdb_movie_tbl where id="+mid+";";
			st.execute(query);
					
		}catch(SQLException e){
			e.printStackTrace();
			throw new DAOException("SQL Exception Occured indeleteStatement");
		}catch(Exception e){
			e.printStackTrace();
			throw new DAOException();
		}finally{
			close(st);
		}
	}
	
	
	public int getLastMovieIndex() throws DAOException{
		int index=-1;
		Statement st=null;
		ResultSet rs=null;
		String query;
		try{
			st=getConnection().createStatement();
			query="select max(id) from anilkumarreddyg_imdb_movie_tbl";
			rs=st.executeQuery(query);
			if(rs.next()){
				index=rs.getInt("id");
			}
		}catch(SQLException e){
			e.printStackTrace();
			throw new DAOException("SQL Exception Occured selectStatement");
		}catch(Exception e){
			e.printStackTrace();
			throw new DAOException();
		}finally{
			close(st);
		}
		return index;
	}
}
